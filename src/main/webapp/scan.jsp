<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.mywmsapp.model.Product, org.example.mywmsapp.model.Place, java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Scan Produit - MyWMSApp</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body>
<div class="container mt-5 text-center">
  <h2>📦 Scanner un Produit</h2>

  <!-- Formulaire de scan -->
  <form action="scan" method="POST" class="mt-4">
    <div class="input-group mb-3">
      <input type="text" name="barcode" class="form-control" placeholder="Entrez le code-barres..." required>
      <button type="submit" class="btn btn-primary"><i class="fa-solid fa-barcode"></i> Scanner</button>
    </div>
  </form>

  <!-- Affichage des messages d'erreur -->
  <%
    String errorMessage = (String) request.getAttribute("error");
    if (errorMessage != null) {
  %>
  <div class="alert alert-danger mt-3">
    <i class="fa-solid fa-triangle-exclamation"></i> <%= errorMessage %>
  </div>
  <%
    }
  %>

  <!-- Affichage du produit scanné -->
  <%
    Product product = (Product) request.getAttribute("product");
    if (product != null) {
  %>
  <div class="card mt-4">
    <div class="card-header bg-success text-white">
      <h5><i class="fa-solid fa-box"></i> Produit Scanné</h5>
    </div>
    <div class="card-body">
      <p><strong>Nom :</strong> <%= product.getName() %></p>
      <p><strong>Code-barres :</strong> <%= product.getBarcode() %></p>
      <p><strong>Dimensions :</strong> <%= product.getWidth() %> x <%= product.getHeight() %> x <%= product.getDepth() %> cm</p>
      <p><strong>Stock :</strong> <%= product.getQuantity() %> unités</p>
    </div>
  </div>

  <!-- Affichage des emplacements disponibles -->
  <%
    List<Place> places = (List<Place>) request.getAttribute("places");
    if (places != null && !places.isEmpty()) {
  %>
  <div class="mt-4">
    <h4><i class="fa-solid fa-map-marker-alt"></i> Emplacements Disponibles</h4>
    <table class="table table-striped table-bordered mt-3">
      <thead class="table-dark">
      <tr>
        <th>#</th>
        <th>Nom de l'Emplacement</th>
        <th>Capacité Disponible</th>
      </tr>
      </thead>
      <tbody>
      <%
        int i = 1;
        for (Place place : places) {
      %>
      <tr>
        <td><%= i++ %></td>
        <td><%= place.getName() %></td>
        <td><%= place.getCapacity() %> unités</td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  </div>
  <%
  } else {
  %>
  <div class="alert alert-warning mt-3">
    <i class="fa-solid fa-exclamation-circle"></i> Aucun emplacement disponible pour ce produit.
  </div>
  <%
    }
  %>
  <%
    }
  %>

  <a href="index.jsp" class="btn btn-secondary mt-4"><i class="fa-solid fa-arrow-left"></i> Retour</a>
</div>
</body>
</html>
