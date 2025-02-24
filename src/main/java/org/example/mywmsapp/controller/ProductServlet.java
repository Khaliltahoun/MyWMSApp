package org.example.mywmsapp.controller;

import org.example.mywmsapp.model.Product;
import org.example.mywmsapp.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String barcode = request.getParameter("barcode");

        if (barcode != null && !barcode.trim().isEmpty()) {
            System.out.println("🔎 Recherche du produit avec le code-barres : " + barcode);

            Product product = productService.getProductByBarcode(barcode); // ✅ Remplacé `searchProductByBarcode()`

            if (product != null) {
                request.setAttribute("product", product);
                System.out.println("✅ Produit trouvé : " + product.getName());
            } else {
                request.setAttribute("error", "Aucun produit trouvé.");
                System.out.println("❌ Aucun produit trouvé pour ce code-barres.");
            }
        } else {
            request.setAttribute("error", "Veuillez entrer un code-barres.");
        }

        request.getRequestDispatcher("/product.jsp").forward(request, response);
    }
}
