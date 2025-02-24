package org.example.mywmsapp.controller;

import org.example.mywmsapp.model.Place;
import org.example.mywmsapp.service.WarehouseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/warehouse")
public class WarehouseServlet extends HttpServlet {
    private final WarehouseService warehouseService = new WarehouseService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Place> places = warehouseService.getAvailablePlaces();

        if (places == null) {
            request.setAttribute("error", "Aucun emplacement trouvé.");
        } else {
            request.setAttribute("places", places);
        }

        request.getRequestDispatcher("warehouse.jsp").forward(request, response);
    }
}
