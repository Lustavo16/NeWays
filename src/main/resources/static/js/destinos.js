document.addEventListener("DOMContentLoaded", () => {

    const btnSalvar = document.getElementById("btnSalvarDestino");

    btnSalvar.addEventListener("click", function () {

        let nome = document.getElementById("nomeDestino").value;
        let endereco = document.getElementById("enderecoDestino").value;

        if (!nome || !endereco) {
            alert("Preencha todos os campos do destino.");
            return;
        }

        let index = document.querySelectorAll("#tabelaDestinos tbody tr").length;

        let linha = `
            <tr>
                <td>${nome}</td>
                <td>${endereco}</td>
            </tr>`;
        document.querySelector("#tabelaDestinos tbody").insertAdjacentHTML('beforeend', linha);

        let hidden = `
            <input type="hidden" name="destinos[${index}].nome" value="${nome}">
            <input type="hidden" name="destinos[${index}].endereco" value="${endereco}">
        `;
        document.getElementById("hiddenDestinos").insertAdjacentHTML('beforeend', hidden);

        let modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("modalDestino"));
        modal.hide();

        document.getElementById("nomeDestino").value = "";
        document.getElementById("enderecoDestino").value = "";
    });
});
