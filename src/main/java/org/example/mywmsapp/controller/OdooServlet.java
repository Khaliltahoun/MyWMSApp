package org.example.mywmsapp.controller;

import org.example.mywmsapp.model.Product;
import org.example.mywmsapp.service.OdooService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/odooProduct")
public class OdooServlet extends HttpServlet {
    private final OdooService odooService = new OdooService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String barcode = request.getParameter("barcode");

        if (barcode == null || barcode.isEmpty()) {
            response.sendRedirect("product.jsp?error=Veuillez entrer un code-barres");
            return;
        }

        Product product = odooService.getProductByBarcode(barcode);

        if (product == null) {
            response.sendRedirect("product.jsp?error=Produit non trouvé");
        } else {
            request.setAttribute("product", product);
            request.getRequestDispatcher("product.jsp").forward(request, response);
        }
    }
}
