<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

  <form action="scan" method="POST" class="mt-4">
    <div class="input-group mb-3">
      <input type="text" name="barcode" class="form-control" placeholder="Entrez le code-barres..." required>
      <button type="submit" class="btn btn-primary"><i class="fa-solid fa-barcode"></i> Scanner</button>
    </div>
  </form>

  <a href="index.jsp" class="btn btn-secondary mt-3"><i class="fa-solid fa-arrow-left"></i> Retour</a>
</div>
</body>
</html>
