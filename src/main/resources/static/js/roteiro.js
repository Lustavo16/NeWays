document.addEventListener("DOMContentLoaded", () => {

    const modalExclusaoElement = document.getElementById("modalExclusao");

    if (!modalExclusaoElement) return;

    const modalExclusao = new bootstrap.Modal(modalExclusaoElement);
    const btnConfirmarExclusao = document.getElementById("btnConfirmarExclusao");
    let idParaExcluir = null;

    const botoesExcluir = document.querySelectorAll(".btn-excluir");

    botoesExcluir.forEach(botao => {
        botao.addEventListener("click", function() {
            idParaExcluir = this.getAttribute("data-id");

            modalExclusao.show();
        });
    });

    if (btnConfirmarExclusao) {
        btnConfirmarExclusao.addEventListener("click", function() {
            if (idParaExcluir) {
                window.location.href = `/roteiro/excluir/${idParaExcluir}`;
            }
        });
    }
});