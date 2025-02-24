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

@WebServlet("/scan")  // ✅ Ensure this matches the form action in scan.jsp
public class BarcodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BarcodeService barcodeService = new BarcodeService();
    private final WarehouseService warehouseService = new WarehouseService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String barcode = request.getParameter("barcode");

        if (barcode == null || barcode.trim().isEmpty()) {
            response.sendRedirect("scan.jsp?error=No barcode provided");
            return;
        }

        // 🔍 Fetch product from database or Odoo
        Product product = barcodeService.scanProduct(barcode);
        if (product == null) {
            response.sendRedirect("scan.jsp?error=Product not found");
            return;
        }

        // 🔄 Get available places for the scanned product
        List<Place> availablePlaces = warehouseService.getAvailablePlacesForProduct(
                product.getWidth(), product.getHeight(), product.getDepth()
        );

        // ✅ Pass product & available places to JSP
        request.setAttribute("product", product);
        request.setAttribute("places", availablePlaces);
        request.getRequestDispatcher("scan.jsp").forward(request, response);
    }
}
