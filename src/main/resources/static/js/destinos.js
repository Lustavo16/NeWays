document.addEventListener("DOMContentLoaded", () => {

    const modalElement = document.getElementById("modalDestino");

    if (!modalElement) {
        console.error("Erro: Modal não encontrado no HTML");
        return;
    }

    const formRoteiro = document.getElementById("formRoteiro");
    const alertaDestinos = document.getElementById("alertaDestinos");

    const modal = bootstrap.Modal.getOrCreateInstance(modalElement);
    const modalTitle = document.querySelector(".modal-title");

    const btnSalvar = document.getElementById("btnSalvarDestino");
    
    const divHidden = document.getElementById("hiddenDestinos");
    const tbody = document.querySelector("#tabelaDestinos tbody");
    const inputEditId = document.getElementById("editId");

    const modalExclusaoElement = document.getElementById("modalExclusao");
    const modalExclusao = modalExclusaoElement ? new bootstrap.Modal(modalExclusaoElement) : null;
    const btnConfirmarExclusao = document.getElementById("btnConfirmarExclusao");
    let idParaExcluir = null;

    const inputNome = document.getElementById("nomeDestino");
    const inputLocalizacao = document.getElementById("localizacaoDestino");
    const inputObservacao = document.getElementById("observacaoDestino");

    modalElement.addEventListener('show.bs.modal', function (event) {
        if (event.relatedTarget) {
            limparModal();
            if(modalTitle) modalTitle.textContent = "Adicionar Destino";
        }
    });

    if (btnSalvar) {
        btnSalvar.addEventListener("click", function (e) {
            e.preventDefault();

            let descricaoValor = $('#descricaoDestino').summernote('code');

            let dados = {
                nome: inputNome.value,
                localizacao: inputLocalizacao.value,
                descricao: descricaoValor,
                observacao: inputObservacao ? inputObservacao.value : "",
                editId: inputEditId.value
            };

            if (!dados.nome || !dados.localizacao || !dados.descricao) {
                alert("Preencha todos os campos.");
                return;
            }

            if (dados.editId) {
                atualizarDestino(dados);
            } else {
                criarDestino(dados);
            }

            modal.hide();
        });
    }

    tbody.addEventListener("click", function (e) {
        const target = e.target;

        // EXCLUIR
        if (target.classList.contains("btn-excluir")) {
            const btnExcluir = e.target.closest('.btn-excluir');
            const id = btnExcluir.getAttribute("data-id");

            idParaExcluir = id;
            modalExclusao.show();
        }

        // EDITAR
        if (target.classList.contains("btn-editar")) {
            const id = target.getAttribute("data-id");
            const hiddenGroup = document.getElementById(`hidden-${id}`);

            const nomeSalvo = hiddenGroup.querySelector(".input-nome").value;
            const localizacaoSalva = hiddenGroup.querySelector(".input-localizacao").value;
            const descricaoSalva = hiddenGroup.querySelector(".input-descricao").value;

            inputEditId.value = id;
            inputNome.value = nomeSalvo;
            inputLocalizacao.value = localizacaoSalva;

            $('#descricaoDestino').summernote('code', descricaoSalva);

            if(inputObservacao) {
                let obsSalva = hiddenGroup.querySelector(".input-observacao") ? hiddenGroup.querySelector(".input-observacao").value : "";
                inputObservacao.value = obsSalva;
            }

            if(modalTitle) modalTitle.textContent = "Editar Destino";
            modal.show();
        }
    });

    if (btnConfirmarExclusao) {
        btnConfirmarExclusao.addEventListener("click", function () {
            if (idParaExcluir) {
                removerDestino(idParaExcluir);
                modalExclusao.hide();
                idParaExcluir = null;
            }
        });
    }

    function removerDestino(id) {
        const linha = document.getElementById(`linha-${id}`);
        const hidden = document.getElementById(`hidden-${id}`);

        if (linha) linha.remove();
        if (hidden) hidden.remove();

        renumerarIndices();
    }

    function criarDestino(dados) {
        const uniqueId = Date.now();
        let index = tbody.rows.length;

        if (alertaDestinos) {
            alertaDestinos.classList.add("d-none");
        }

        let linha = `
            <tr id="linha-${uniqueId}">
                <td class="td-nome">${dados.nome}</td>
                <td class="td-localizacao">${dados.localizacao}</td>
                <td>
                   <span class="text-muted small fst-italic">Novo item (salve para enviar)</span>
                   <input type="file" class="form-control form-control-sm mt-1" 
                          accept="image/*">
                </td>
                <td>
                    <button type="button" class="btn btn-primary btn-sm btn-editar" data-id="${uniqueId}">Editar</button>
                    <button type="button" class="btn btn-danger btn-sm btn-excluir" data-id="${uniqueId}">Excluir</button>
                </td>
            </tr>`;
        tbody.insertAdjacentHTML('beforeend', linha);

        let hidden = `
            <div id="hidden-${uniqueId}" class="hidden-group">
                <input type="hidden" class="input-nome" name="destinos[${index}].nome" value="${dados.nome}">
                <input type="hidden" class="input-localizacao" name="destinos[${index}].localizacao" value="${dados.localizacao}">
                
                <input type="hidden" class="input-descricao" name="destinos[${index}].descricao">
                <input type="hidden" class="input-observacao" name="destinos[${index}].observacao" value="${dados.observacao}">
            </div>`;

        divHidden.insertAdjacentHTML('beforeend', hidden);
        document.querySelector(`#hidden-${uniqueId} .input-descricao`).value = dados.descricao;

        renumerarIndices();
    }

    function atualizarDestino(dados) {
        const linha = document.getElementById(`linha-${dados.editId}`);
        linha.querySelector(".td-nome").textContent = dados.nome;
        linha.querySelector(".td-localizacao").textContent = dados.localizacao;

        const hiddenGroup = document.getElementById(`hidden-${dados.editId}`);
        hiddenGroup.querySelector(".input-nome").value = dados.nome;
        hiddenGroup.querySelector(".input-localizacao").value = dados.localizacao;

        hiddenGroup.querySelector(".input-descricao").value = dados.descricao;
        hiddenGroup.querySelector(".input-observacao").value = dados.observacao;
    }

    function limparModal() {
        inputNome.value = "";
        inputLocalizacao.value = "";
        $('#descricaoDestino').summernote('code', '');
        if(inputObservacao) inputObservacao.value = "";
        inputEditId.value = "";
    }

    function renumerarIndices() {
        const linhas = tbody.querySelectorAll("tr");

        linhas.forEach((tr, index) => {
            const btnEditar = tr.querySelector(".btn-editar");
            if (!btnEditar) return;

            const idVinculo = btnEditar.getAttribute("data-id");
            const hiddenGroup = document.getElementById(`hidden-${idVinculo}`);

            if (hiddenGroup) {
                setPropName(hiddenGroup, ".input-id", index, "id");
                setPropName(hiddenGroup, ".input-nome", index, "nome");
                setPropName(hiddenGroup, ".input-localizacao", index, "localizacao");
                setPropName(hiddenGroup, ".input-descricao", index, "descricao");
                setPropName(hiddenGroup, ".input-observacao", index, "observacao");
            }

            const fileInput = tr.querySelector("input[type='file']");
            if (fileInput) {
                fileInput.name = `destinos[${index}].arquivoDestino`;
            }
        });
    }

    function setPropName(container, selector, index, campo) {
        const input = container.querySelector(selector);
        if (input) {
            input.name = `destinos[${index}].${campo}`;
        }
    }

    if (formRoteiro) {
        formRoteiro.addEventListener("submit", function(e) {
            const qtdDestinos = document.querySelectorAll("#tabelaDestinos tbody tr").length;

            if (qtdDestinos === 0) {
                e.preventDefault();

                if (alertaDestinos) {
                    alertaDestinos.classList.remove("d-none");

                    alertaDestinos.scrollIntoView({ behavior: "smooth", block: "center" });
                } else {
                    alert("Adicione pelo menos um destino!");
                }
            } else {
                if (alertaDestinos) {
                    alertaDestinos.classList.add("d-none");
                }
            }
        });
    }
});