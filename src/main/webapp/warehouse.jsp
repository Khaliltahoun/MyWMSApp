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
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Emplacements - MyWMSApp</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body>
<div class="container mt-5">
  <h2 class="text-center">📍 Emplacements Disponibles</h2>

  <% if (error != null) { %>
  <p class="alert alert-danger text-center"><%= error %></p>
  <% } else if (places == null || places.isEmpty()) { %>
  <p class="alert alert-warning text-center">Aucun emplacement disponible.</p>
  <% } else { %>

  <table class="table table-hover table-striped mt-3">
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
    <tr class="<%= place.isOccupied() ? "table-danger" : "table-success" %>">
      <td><%= place.getId() %></td>
      <td><%= place.getLocationCode() %></td>
      <td><%= place.getMaxWidth() %> x <%= place.getMaxHeight() %> x <%= place.getMaxDepth() %></td>
      <td>
        <% if (place.isOccupied()) { %>
        <span class="badge bg-danger"><i class="fa-solid fa-box"></i> Occupé</span>
        <% } else { %>
        <span class="badge bg-success"><i class="fa-solid fa-box-open"></i> Disponible</span>
        <% } %>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <% } %>

  <a href="index.jsp" class="btn btn-secondary mt-3"><i class="fa-solid fa-arrow-left"></i> Retour</a>
</div>
</body>
</html>
