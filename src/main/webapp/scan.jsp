<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.mywmsapp.model.Product, org.example.mywmsapp.model.Place, java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>📦 Scan Produit - MyWMSApp</title>

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
      max-width: 800px;
      width: 100%;
    }

    /* 🌟 Scan Form */
    .scan-form .input-group input {
      border-right: none;
    }

    .scan-form .input-group .btn {
      border-left: none;
    }

    /* 🌟 Cards */
    .card {
      border-radius: 12px;
      box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
      margin-top: 20px;
    }

    .card-header {
      font-size: 1.2rem;
      font-weight: bold;
    }

    /* 🌟 Places Grid */
    .place-row {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      gap: 5px;
      margin-top: 15px;
    }

    .place-box {
      width: 40px;
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 4px;
      font-weight: bold;
      color: white;
      cursor: pointer;
    }

    .free { background-color: #28a745; } /* Green */
    .occupied { background-color: #dc3545; } /* Red */

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
  <h1><i class="fa-solid fa-box"></i> Scanner un Produit</h1>
</header>

<br/>
<br/>

<!-- 🌟 Main Container -->
<div class="container">
  <div class="content-box">

    <!-- 🔍 Scan Form -->
    <form action="scan" method="POST" class="scan-form">
      <div class="input-group mb-4">
        <input type="text" name="barcode" class="form-control" placeholder="📌 Entrez le code-barres..." required>
        <button type="submit" class="btn btn-primary"><i class="fa-solid fa-barcode"></i> Scanner</button>
      </div>
    </form>

    <!-- ⚠️ Display Error Message -->
    <% String errorMessage = (String) request.getAttribute("error"); %>
    <% if (errorMessage != null) { %>
    <div class="alert alert-danger text-center">
      <i class="fa-solid fa-triangle-exclamation"></i> <%= errorMessage %>
    </div>
    <% } %>

    <!-- ✅ Display Scanned Product -->
    <% Product product = (Product) request.getAttribute("product"); %>
    <% if (product != null) { %>
    <div class="card">
      <div class="card-header bg-success text-white text-center">
        <i class="fa-solid fa-box"></i> Produit Scanné
      </div>
      <div class="card-body text-center">
        <p><strong>📝 Nom :</strong> <%= product.getName() %></p>
        <p><strong>📌 Code-barres :</strong> <%= product.getBarcode() %></p>
        <p><strong>📏 Dimensions :</strong> <%= product.getWidth() %> x <%= product.getHeight() %> x <%= product.getDepth() %> cm</p>
        <p><strong>🏷️ Catégorie :</strong> <%= product.getCategory() %> </p>
      </div>
    </div>

    <!-- 🔹 Bouton "Stocker Automatiquement" -->
    <form action="scan" method="POST">
      <input type="hidden" name="barcode" value="<%= product.getBarcode() %>">
      <input type="hidden" name="action" value="autoStock">
      <button type="submit" class="btn btn-primary mt-3">
        <i class="fa-solid fa-robot"></i> Stocker Automatiquement
      </button>
    </form>


    <!-- 📍 Display Available & Occupied Storage Locations -->
    <% List<Place> places = (List<Place>) request.getAttribute("places"); %>
    <% if (places != null && !places.isEmpty()) { %>
    <div class="mt-4">
      <h4 class="text-center"><i class="fa-solid fa-map-marker-alt"></i> Emplacements Disponibles</h4>
      <div class="place-row mt-3">
        <% for (Place place : places) { %>
        <div class="place-box <%= place.isOccupied() ? "occupied" : "free" %>"
             title="Section <%= place.getSectionId() %> | Rangée <%= place.getRowIndex() %> | Colonne <%= place.getColIndex() %>">
          <%= place.getRowIndex() %>-<%= place.getColIndex() %>
        </div>
        <% } %>
      </div>
    </div>

    <h3 class="text-center mt-4">📝 Stocker un Produit Manuellement</h3>

    <!-- 📦 Table of Storage Options (Only Free Places) -->
    <table class="table table-bordered mt-4">
      <thead class="table-dark">
      <tr>
        <th>#</th>
        <th>Nom de l'Emplacement</th>
        <th>Rangée</th>
        <th>Colonne</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <% int i = 1; %>
      <% for (Place place : places) {
        if (!place.isOccupied()) { // Show only free places in table
      %>
      <tr>
        <td class="text-center"><%= i++ %></td>
        <td class="text-center"><%= place.getName() %></td>
        <td class="text-center"><%= place.getRowIndex() %></td>
        <td class="text-center"><%= place.getColIndex() %></td>
        <td class="text-center">
          <form action="storeProduct" method="POST">
            <input type="hidden" name="barcode" value="<%= product.getBarcode() %>">
            <input type="hidden" name="placeId" value="<%= place.getId() %>">
            <input type="hidden" name="sectionId" value="<%= place.getSectionId() %>">
            <button type="submit" class="btn btn-success"><i class="fa-solid fa-box"></i> Stocker</button>
          </form>


        </td>
      </tr>
      <% }} %>
      </tbody>
    </table>
    <% } %>
    <% } %>

    <!-- 🔙 Back Button -->
    <div class="text-center mt-4">
      <a href="index.jsp" class="btn btn-secondary"><i class="fa-solid fa-arrow-left"></i> Retour</a>
    </div>

  </div>
</div>

<br/>
<br/>

<!-- 🌟 Footer -->
<footer>
  <p>© 2025 MyWMSApp | Gestion des Stocks & Logistique</p>
</footer>

</body>
</html>

