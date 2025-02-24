<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.mywmsapp.model.Product" %>
<html>
<head>
  <title>Search Product</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container">
<h2 class="mt-4">Search for a Product</h2>

<form action="product" method="get" class="mb-4">
  <input type="text" name="barcode" placeholder="Enter barcode" class="form-control w-50 d-inline">
  <button type="submit" class="btn btn-primary">Search</button>
</form>

<% if (request.getAttribute("error") != null) { %>
<div class="alert alert-danger"><%= request.getAttribute("error") %></div>
<% } %>

<% Product product = (Product) request.getAttribute("product");
  if (product != null) { %>
<h4>✅ Product Found</h4>
<table class="table table-bordered">
  <tr><th>ID</th><td><%= product.getId() %></td></tr>
  <tr><th>Name</th><td><%= product.getName() %></td></tr>
  <tr><th>Barcode</th><td><%= product.getBarcode() %></td></tr>
  <tr><th>Width</th><td><%= product.getWidth() %></td></tr>
  <tr><th>Height</th><td><%= product.getHeight() %></td></tr>
  <tr><th>Depth</th><td><%= product.getDepth() %></td></tr>
  <tr><th>Quantity</th><td><%= product.getQuantity() %></td></tr>
</table>
<% } else { %>
<% } %>
</body>
</html>
