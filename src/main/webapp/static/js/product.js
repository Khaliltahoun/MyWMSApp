document.addEventListener("DOMContentLoaded", function () {
    let productDetails = document.getElementById("product-details");

    if (productDetails) {
        let barcode = document.getElementById("barcode").value;

        fetch("/product?barcode=" + encodeURIComponent(barcode))
            .then(response => response.text())
            .then(data => {
                productDetails.innerHTML = data;
            })
            .catch(error => console.error("Erreur lors du chargement du produit:", error));
    }
});
