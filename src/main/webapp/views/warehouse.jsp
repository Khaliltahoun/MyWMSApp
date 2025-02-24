<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, org.example.mywmsapp.model.Place" %>
<%
  List<Place> places = (List<Place>) request.getAttribute("places");
  String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Emplacements - MyWMSApp</title>
  <link rel="stylesheet" href="css/warehouse.css">
</head>
<body>
<div class="container mt-4">
  <h2 class="text-center">📍 Emplacements Disponibles</h2>

  <% if (error != null) { %>
  <p class="alert alert-danger"><%= error %></p>
  <% } else if (places == null || places.isEmpty()) { %>
  <p class="alert alert-warning">Aucun emplacement disponible.</p>
  <% } else { %>

  <table class="table table-bordered warehouse-table mt-3">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Code Emplacement</th>
      <th>Dimensions (LxHxP)</th>
      <th>Statut</th>
    </tr>
    </thead>
    <tbody>
    <% for (Place place : places) { %>
    <tr class="<%= place.isOccupied() ? "occupied" : "available" %>">
      <td><%= place.getId() %></td>
      <td><%= place.getLocationCode() %></td>
      <td><%= place.getMaxWidth() %> x <%= place.getMaxHeight() %> x <%= place.getMaxDepth() %></td>
      <td><%= place.isOccupied() ? "❌ Occupé" : "✅ Disponible" %></td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <% } %>

  <a href="index.jsp" class="btn btn-secondary mt-3">Retour</a>
</div>
</body
