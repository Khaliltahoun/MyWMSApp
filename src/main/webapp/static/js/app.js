document.addEventListener("DOMContentLoaded", function () {
    console.log("App.js chargé 🚀");

    // Animation sur les boutons
    const buttons = document.querySelectorAll("button");
    buttons.forEach(button => {
        button.addEventListener("mouseover", function () {
            this.style.transform = "scale(1.05)";
        });
        button.addEventListener("mouseout", function () {
            this.style.transform = "scale(1)";
        });
    });

    // AJAX pour le scan du produit
    const scanForm = document.querySelector("form[action='scan']");
    if (scanForm) {
        scanForm.addEventListener("submit", function (event) {
            event.preventDefault();
            let barcode = document.querySelector("input[name='barcode']").value;
            let resultContainer = document.getElementById("scan-result");

            if (barcode.trim() !== "") {
                resultContainer.innerHTML = "<p class='loading'>📡 Scan en cours...</p>";
                fetch("scan?barcode=" + barcode)
                    .then(response => response.text())
                    .then(data => {
                        resultContainer.innerHTML = data;
                    })
                    .catch(error => {
                        resultContainer.innerHTML = "<p class='error'>❌ Erreur lors du scan.</p>";
                        console.error("Erreur:", error);
                    });
            }
        });
    }

    // Mise en évidence des emplacements disponibles
    const availablePlaces = document.querySelectorAll(".available");
    availablePlaces.forEach(place => {
        place.addEventListener("mouseover", function () {
            this.style.backgroundColor = "#d4edda";
            this.style.color = "#155724";
        });
        place.addEventListener("mouseout", function () {
            this.style.backgroundColor = "";
            this.style.color = "";
        });
    });
});
