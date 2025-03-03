<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.mywmsapp.model.Product" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Recherche Produit - MyWMSApp</title>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

  <style>
    /* 🌟 Global Styles */
    html, body {
      height: 100%;
      margin: 0;
      font-family: 'Arial', sans-serif;
      background-color: #f8f9fa;
      display: flex;
      flex-direction: column;
    }

    /* 🌟 Header */
    .header {
      background: linear-gradient(135deg, #007bff, #0056b3);
      color: white;
      padding: 30px 0;
      text-align: center;
      border-bottom-left-radius: 15px;
      border-bottom-right-radius: 15px;
    }

    .header h1 {
      font-size: 2rem;
      font-weight: bold;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 10px;
    }

    .header i {
      font-size: 1.5rem;
    }

    /* 🌟 Main Container */
    .container {
      flex-grow: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      text-align: center;
    }

    .content-box {
      max-width: 600px;
      width: 100%;
    }

    /* 🌟 Search Form */
    .search-form .input-group input {
      border-right: none;
    }

    .search-form .input-group .btn {
      border-left: none;
    }

    /* 🌟 Product Card */
    .card {
      border-radius: 12px;
      box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
      margin-top: 20px;
    }

    .card-header {
      font-size: 1.2rem;
      font-weight: bold;
    }

    /* 🌟 Product Table */
    .product-table th {
      background-color: #007bff;
      color: white;
      text-align: center;
    }

    /* 🌟 Footer */
    footer {
      background: #343a40;
      color: white;
      padding: 15px;
      text-align: center;
      margin-top: auto;
      width: 100%;
      border-top-left-radius: 20px;
      border-top-right-radius: 20px;
    }
  </style>
</head>

<body>

<!-- 🌟 Header -->
<header class="header">
  <h1><i class="fa-solid fa-search"></i> Rechercher un Produit</h1>
</header>

<!-- 🌟 Main Container -->
<div class="container">
  <div class="content-box">

    <!-- 🔍 Search Form -->
    <form action="product" method="get" class="search-form">
      <div class="input-group mb-4">
        <input type="text" name="barcode" placeholder="📌 Entrez le code-barres" class="form-control" required>
        <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> Rechercher</button>
      </div>
    </form>

    <!-- ⚠️ Error Message -->
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger text-center">
      <i class="fa-solid fa-triangle-exclamation"></i> <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <!-- ✅ Product Found -->
    <% Product product = (Product) request.getAttribute("product"); %>
    <% if (product != null) { %>
    <div class="card">
      <div class="card-header bg-success text-white text-center">
        <i class="fa-solid fa-check-circle"></i> Produit Trouvé
      </div>
      <div class="card-body text-center">
        <table class="table table-bordered product-table mt-3">
          <tr><th>ID</th><td><%= product.getId() %></td></tr>
          <tr><th>Nom</th><td><%= product.getName() %></td></tr>
          <tr><th>Code-Barres</th><td><%= product.getBarcode() %></td></tr>
          <tr><th>Largeur</th><td><%= product.getWidth() %> cm</td></tr>
          <tr><th>Hauteur</th><td><%= product.getHeight() %> cm</td></tr>
          <tr><th>Profondeur</th><td><%= product.getDepth() %> cm</td></tr>
          <tr><th>Quantité</th><td><%= String.format("%.2f", product.getQuantity()) %></td></tr>
          <tr><th>Catégorie</th><td><%= product.getCategory() %></td></tr>
        </table>
      </div>
    </div>
    <% } else if (request.getAttribute("error") == null) { %>
    <p class="text-center text-muted mt-4">
      <i class="fa-solid fa-box-open"></i> Aucun produit trouvé.
    </p>
    <% } %>

    <!-- 🔙 Back Button -->
    <div class="text-center mt-4">
      <a href="index.jsp" class="btn btn-secondary"><i class="fa-solid fa-arrow-left"></i> Retour</a>
    </div>

  </div>
</div>

<!-- 🌟 Footer -->
<footer>
  <p>© 2025 MyWMSApp | Gestion des Stocks & Logistique</p>
</footer>

</body>
</html>
