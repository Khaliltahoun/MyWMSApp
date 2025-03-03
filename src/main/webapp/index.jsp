<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - MyWMSApp</title>
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

    /* 🌟 Navbar */
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

    /* 🌟 Main Content */
    .container {
      flex-grow: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      text-align: center;
    }

    .dashboard-container {
      max-width: 1100px;
      width: 100%;
    }

    /* 🌟 Cards */
    .dashboard-card {
      background: white;
      border-radius: 12px;
      padding: 25px;
      text-align: center;
      box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .dashboard-card:hover {
      transform: translateY(-5px);
      box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.2);
    }

    .dashboard-card h5 {
      font-weight: bold;
      font-size: 1.3rem;
    }

    .dashboard-card .btn {
      font-size: 1rem;
      padding: 10px;
      width: 100%;
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

<!-- 🌟 Navbar -->
<header class="header">
  <h1><i class="fa-solid fa-warehouse"></i> MyWMSApp - Gestion d'Entrepôt</h1>
</header>

<!-- 🌟 Main Content -->
<div class="container">
  <div class="dashboard-container">
    <div class="row g-4">
      <!-- 🔹 Emplacements Disponibles -->
      <div class="col-md-4">
        <div class="dashboard-card">
          <h5><i class="fa-solid fa-map-marker-alt text-primary"></i> Emplacements Disponibles</h5>
          <p>Consultez les emplacements libres et optimisez votre stockage.</p>
          <a href="warehouse" class="btn btn-primary">Voir les Emplacements</a>
        </div>
      </div>

      <!-- 🔹 Scanner un Produit -->
      <div class="col-md-4">
        <div class="dashboard-card">
          <h5><i class="fa-solid fa-barcode text-success"></i> Scanner un Produit</h5>
          <p>Scannez un produit et trouvez son emplacement en temps réel.</p>
          <a href="scan.jsp" class="btn btn-success">Scanner</a>
        </div>
      </div>

      <!-- 🔹 Recherche Produit -->
      <div class="col-md-4">
        <div class="dashboard-card">
          <h5><i class="fa-solid fa-search text-warning"></i> Recherche Produit</h5>
          <p>Recherchez un produit par code-barres et consultez ses détails.</p>
          <a href="product.jsp" class="btn btn-warning">Rechercher</a>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 🌟 Footer -->
<footer>
  <p>© 2025 MyWMSApp | Gestion des Stocks & Logistique</p>
</footer>

</body>
</html>
