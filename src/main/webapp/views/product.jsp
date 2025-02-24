<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, org.example.mywmsapp.model.Product" %>
<html>
<head>
  <title>Product Search</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container">
<h2 class="mt-4">Product Search</h2>

<form action="product" method="get" class="mb-4">
  <input type="text" name="query" placeholder="Enter product name or barcode" class="form-control w-50 d-inline">
  <button type="submit" class="btn btn-primary">Search</button>
</form>

<% if (request.getAttribute("error") != null) { %>
<div class="alert alert-danger"><%= request.getAttribute("error") %></div>
<% } %>

<% List<Product> products = (List<Product>) request.getAttribute("products");
  if (products != null && !products.isEmpty()) { %>
<table class="table table-bordered">
  <thead>
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Barcode</th>
    <th>Width</th>
    <th>Height</th>
    <th>Depth</th>
    <th>Quantity</th>
  </tr>
  </thead>
  <tbody>
  <% for (Product product : products) { %>
  <tr>
    <td><%= product.getId() %></td>
    <td><%= product.getName() %></td>
    <td><%= product.getBarcode() %></td>
    <td><%= product.getWidth() %></td>
    <td><%= product.getHeight() %></td>
    <td><%= product.getDepth() %></td>
    <td><%= product.getQuantity() %></td>
  </tr>
  <% } %>
  </tbody>
</table>
<% } %>
</body>
</html>
