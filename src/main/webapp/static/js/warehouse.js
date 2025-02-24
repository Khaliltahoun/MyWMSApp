document.addEventListener("DOMContentLoaded", function () {
    function fetchWarehouseData() {
        fetch("/warehouse")
            .then(response => response.text())
            .then(data => {
                document.getElementById("warehouse-content").innerHTML = data;
            })
            .catch(error => console.error("Erreur lors du chargement de l'entrepôt:", error));
    }

    fetchWarehouseData();
    setInterval(fetchWarehouseData, 10000); // Rafraîchit toutes les 10 secondes
});
