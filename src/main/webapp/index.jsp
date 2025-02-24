<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - MyWMSApp</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
  <link rel="stylesheet" href="static/css/style.css">
</head>
<body>
<nav class="navbar navbar-dark bg-primary p-3">
  <a class="navbar-brand text-white" href="#">📦 MyWMSApp - Gestion d'Entrepôt</a>
</nav>

<div class="container mt-5">
  <h2 class="text-center mb-4">Bienvenue sur votre système de gestion</h2>

  <div class="row">
    <div class="col-md-4">
      <div class="card shadow p-3 mb-4">
        <h5 class="card-title text-center">📍 Emplacements Disponibles</h5>
        <p class="card-text text-center">Consultez les emplacements libres.</p>
        <a href="warehouse.jsp" class="btn btn-primary d-block">Voir les Emplacements</a>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card shadow p-3 mb-4">
        <h5 class="card-title text-center">📦 Scanner un Produit</h5>
        <p class="card-text text-center">Scannez et trouvez un emplacement adapté.</p>
        <a href="scan.jsp" class="btn btn-success d-block">Scanner</a>
      </div>
    </div>

    <div class="col-md-4">
      <div class="card shadow p-3 mb-4">
        <h5 class="card-title text-center">🔍 Recherche Produit</h5>
        <p class="card-text text-center">Cherchez un produit par code-barres.</p>
        <a href="product.jsp" class="btn btn-warning d-block">Rechercher</a>
      </div>
    </div>
  </div>
</div>

<footer class="bg-dark text-white text-center py-3 mt-4">
  © 2025 MyWMSApp | Gestion des Stocks & Logistique
</footer>
</body>
</html>
