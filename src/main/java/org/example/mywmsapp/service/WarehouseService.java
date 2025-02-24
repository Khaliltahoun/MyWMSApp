package org.example.mywmsapp.service;

import org.example.mywmsapp.dao.WarehouseDAO;
import org.example.mywmsapp.model.Place;
import java.util.List;

public class WarehouseService {
    private final WarehouseDAO warehouseDAO = new WarehouseDAO();

    public List<Place> getAvailablePlaces() {
        return warehouseDAO.getAvailablePlaces();
    }

    // ✅ Ajoute cette méthode pour rechercher des emplacements adaptés
    public List<Place> getAvailablePlacesForProduct(double width, double height, double depth) {
        return warehouseDAO.getSuitablePlaces(width, height, depth);
    }
}
