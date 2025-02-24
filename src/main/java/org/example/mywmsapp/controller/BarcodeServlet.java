package org.example.mywmsapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mywmsapp.model.Product;
import org.example.mywmsapp.model.Place;
import org.example.mywmsapp.service.BarcodeService;
import org.example.mywmsapp.service.WarehouseService;

import java.io.IOException;
import java.util.List;

@WebServlet("/scan")  // ✅ Assurez-vous que cela correspond au formulaire dans scan.jsp
public class BarcodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BarcodeService barcodeService = new BarcodeService();
    private final WarehouseService warehouseService = new WarehouseService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String barcode = request.getParameter("barcode");

        if (barcode == null || barcode.trim().isEmpty()) {
            request.setAttribute("error", "Aucun code-barres fourni !");
            request.getRequestDispatcher("scan.jsp").forward(request, response);
            return;
        }

        // 🔍 Recherche du produit dans la base de données ou via Odoo
        Product product = barcodeService.scanProduct(barcode);
        if (product == null) {
            request.setAttribute("error", "Produit non trouvé !");
            request.getRequestDispatcher("scan.jsp").forward(request, response);
            return;
        }

        // 🔄 Recherche des emplacements disponibles pour stocker le produit
        List<Place> availablePlaces = warehouseService.getAvailablePlacesForProduct(
                product.getWidth(), product.getHeight(), product.getDepth()
        );

        // ✅ Envoi des données à la page JSP
        request.setAttribute("product", product);
        request.setAttribute("places", availablePlaces);
        request.getRequestDispatcher("scan.jsp").forward(request, response);
    }
}
