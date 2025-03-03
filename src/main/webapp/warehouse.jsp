<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.Map, org.example.mywmsapp.model.Place, org.example.mywmsapp.model.Product" %>
<%
  Object sectionPlacesMapObj = request.getAttribute("sectionPlacesMap");
  Map<Integer, List<Place>> sectionPlacesMap = null;
  if (sectionPlacesMapObj instanceof Map<?, ?>) {
    sectionPlacesMap = (Map<Integer, List<Place>>) sectionPlacesMapObj;
  }

  String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>🏢 Entrepôt - MyWMSApp</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

  <style>
    .container {
      max-width: 1100px;
      margin: auto;
    }

    .place {
      width: 40px;
      height: 40px;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      font-weight: bold;
      margin: 2px;
      border-radius: 5px;
      color: white;
      cursor: pointer;
    }
    .occupied { background-color: #dc3545; }  /* 🔴 Red = Occupied */
    .free { background-color: #28a745; }      /* 🟢 Green = Free */

    /* 🔹 Section Header */
    .section-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 100%;
      margin-top: 20px;
    }
    .section-header {
      background-color: #007bff;
      color: white;
      text-align: center;
      font-size: 1.3rem;
      font-weight: bold;
      padding: 12px;
      border-radius: 8px;
      width: 100%;
      max-width: calc(27 * 44px);
    }

    /* 🔹 Storage Grid */
    .grid-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 5px;
      padding: 10px;
    }
    .row-container {
      display: flex;
      justify-content: center;
      gap: 3px;
    }

    /* 🔹 Modal */
    .modal-content {
      border-radius: 10px;
      padding: 20px;
    }
  </style>
</head>

<body>
<div class="container mt-4">
  <h2 class="text-center">🏢 Gestion des Emplacements</h2>

  <!-- 🔴 Error Message -->
  <% if (error != null) { %>
  <p class="alert alert-danger text-center"><i class="fa-solid fa-triangle-exclamation"></i> <%= error %></p>
  <% } %>

  <!-- 🚨 No Storage Locations Available -->
  <% if (sectionPlacesMap == null || sectionPlacesMap.isEmpty()) { %>
  <p class="alert alert-warning text-center">Aucun emplacement disponible.</p>
  <% } else { %>

  <!-- 🔄 Loop Through Each Section -->
  <% for (int categoryId = 1; categoryId <= 4; categoryId++) {
    List<Place> places = sectionPlacesMap.get(categoryId);
  %>

  <div class="section-container">
    <div class="section-header"><i class="fa-solid fa-warehouse"></i> Section <%= categoryId %></div>

    <div class="grid-container">
      <% if (places != null && !places.isEmpty()) {
        int rowCounter = 0;
      %>

      <!-- 📌 Display Places in Rows of 25 -->
      <div class="row-container">
        <% for (Place place : places) { %>
        <div class="place <%= place.isOccupied() ? "occupied" : "free" %>"
             title="Rangée: <%= place.getRowIndex() %>, Colonne: <%= place.getColIndex() %>"
             onclick="showPlaceDetails('<%= place.getId() %>', '<%= place.getRowIndex() %>-<%= place.getColIndex() %>', <%= place.isOccupied() ? 1 : 0 %>)">
          <%= place.getRowIndex() %>-<%= place.getColIndex() %>
        </div>
        <% rowCounter++;
          if (rowCounter % 25 == 0) { %>
      </div><div class="row-container"> <!-- Start a new row after 25 places -->
      <% }
      } %>
    </div>

      <% } else { %>
      <p class="text-center">Pas d'emplacements disponibles.</p>
      <% } %>
    </div>

  </div> <!-- End of Section Container -->

  <% } %>
  <% } %>

  <!-- 🔙 Back Button -->
  <div class="text-center mt-4">
    <a href="index.jsp" class="btn btn-secondary"><i class="fa-solid fa-arrow-left"></i> Retour</a>
  </div>
</div>

<!-- 📦 MODAL FOR PLACE DETAILS -->
<div class="modal fade" id="placeModal" tabindex="-1" aria-labelledby="placeModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="placeModalLabel"><i class="fa-solid fa-box"></i> Détails de l'Emplacement</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p><strong>📌 Emplacement :</strong> <span id="placeNumber"></span></p>
        <div id="storedProductInfo"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
        <form id="freePlaceForm" action="freePlace" method="POST">
          <input type="hidden" name="placeId" id="placeIdInput">
          <button type="submit" class="btn btn-danger" id="freePlaceButton"><i class="fa-solid fa-trash"></i> Libérer</button>
        </form>
      </div>
    </div>
  </div>
</div>

<!-- 🌟 Script for Modal -->
<script>
  function showPlaceDetails(placeId, placeNumber, isOccupied) {
    document.getElementById("placeNumber").innerText = placeNumber;
    document.getElementById("placeIdInput").value = placeId;


    var myModal = new bootstrap.Modal(document.getElementById('placeModal'));
    myModal.show();
  }
</script>


<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
