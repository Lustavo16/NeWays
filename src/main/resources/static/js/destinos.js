document.addEventListener("DOMContentLoaded", () => {

    const modalElement = document.getElementById("modalDestino");

    if (!modalElement) {
        console.error("Erro: Modal não encontrado no HTML");
        return;
    }

    const modal = bootstrap.Modal.getOrCreateInstance(modalElement);
    const modalTitle = document.querySelector(".modal-title");

    const btnSalvar = document.getElementById("btnSalvarDestino");
    
    const divHidden = document.getElementById("hiddenDestinos");
    const tbody = document.querySelector("#tabelaDestinos tbody");
    const inputEditId = document.getElementById("editId");

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
            const id = target.getAttribute("data-id");
            document.getElementById(`linha-${id}`).remove();
            document.getElementById(`hidden-${id}`).remove();
            renumerarIndices();
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

    function criarDestino(dados) {
        const uniqueId = Date.now();
        let index = tbody.rows.length;

        let linha = `
            <tr id="linha-${uniqueId}">
                <td class="td-nome">${dados.nome}</td>
                <td class="td-localizacao">${dados.localizacao}</td>
                <td>
                    <input type="file" class="form-control form-control-sm" 
                           name="destinos[${index}].arquivoDestino" accept="image/*">
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
        const gruposHidden = document.querySelectorAll(".hidden-group");
        gruposHidden.forEach((grupo, index) => {
            grupo.querySelector(".input-nome").name = `destinos[${index}].nome`;
            grupo.querySelector(".input-localizacao").name = `destinos[${index}].localizacao`;

            grupo.querySelector(".input-descricao").name = `destinos[${index}].descricao`;
            grupo.querySelector(".input-observacao").name = `destinos[${index}].observacao`;
        });

        const linhas = tbody.querySelectorAll("tr");
        linhas.forEach((tr, index) => {
            const fileInput = tr.querySelector("input[type='file']");
            if(fileInput) {
                fileInput.name = `destinos[${index}].arquivoDestino`;
            }
        });
    }
});