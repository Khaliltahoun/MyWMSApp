package org.example.mywmsapp.controller;

import org.example.mywmsapp.model.Product;
import org.example.mywmsapp.model.Section;
import org.example.mywmsapp.service.OdooService;
import org.example.mywmsapp.service.ProductService;
import org.example.mywmsapp.service.SectionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/odooProduct")
public class OdooServlet extends HttpServlet {
    private final OdooService odooService = new OdooService();
    private final ProductService productService = new ProductService();
    private final SectionService sectionService = new SectionService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String barcode = request.getParameter("barcode");

        if (barcode == null || barcode.trim().isEmpty()) {
            response.sendRedirect("product.jsp?error=Veuillez entrer un code-barres");
            return;
        }

        // 🔍 Recherche du produit dans Odoo
        Product product = odooService.getProductByBarcode(barcode);

        if (product == null) {
            response.sendRedirect("product.jsp?error=Produit non trouvé dans Odoo");
            return;
        }

        // 🔄 Vérification et mise à jour de la catégorie du produit en base locale
        Product existingProduct = productService.getProductByBarcode(barcode);
        if (existingProduct == null) {
            productService.saveProduct(product); // Ajoute le produit localement s'il n'existe pas
        } else if (existingProduct.getCategory() != product.getCategory()) {
            productService.updateProductCategory(barcode, product.getCategory()); // Mise à jour si nécessaire
        }

        // 📌 Déterminer la section de stockage
        Section section = sectionService.getSectionByCategory(product.getCategory());
        if (section == null) {
            response.sendRedirect("product.jsp?error=Aucune section correspondante trouvée !");
            return;
        }

        // ✅ Envoi des données à la page JSP
        request.setAttribute("product", product);
        request.setAttribute("section", section);
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }
}
