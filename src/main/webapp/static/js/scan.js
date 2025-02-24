document.addEventListener("DOMContentLoaded", function () {
    let scanForm = document.getElementById("scan-form");

    if (scanForm) {
        scanForm.addEventListener("submit", function (event) {
            event.preventDefault();

            let barcode = document.getElementById("barcode").value;
            if (!barcode) {
                alert("Veuillez entrer un code-barres !");
                return;
            }

            fetch("/scan", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "barcode=" + encodeURIComponent(barcode)
            })
                .then(response => response.text())
                .then(data => {
                    document.getElementById("scan-result").innerHTML = data;
                })
                .catch(error => console.error("Erreur lors du scan:", error));
        });
    }
});
