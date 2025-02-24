package org.example.mywmsapp.service;

import org.example.mywmsapp.dao.WarehouseDAO;
import org.example.mywmsapp.model.Place;

import java.util.List;

public class WarehouseService {
    private final WarehouseDAO warehouseDAO = new WarehouseDAO();

    public List<Place> getAvailablePlaces() {
        return warehouseDAO.getAvailablePlaces();
    }

    // ✅ Méthode pour rechercher les emplacements adaptés à un produit en fonction de ses dimensions
    public List<Place> getAvailablePlacesForProduct(double width, double height, double depth) {
        System.out.println("🔍 Recherche des emplacements pour un produit de taille : "
                + width + "x" + height + "x" + depth);
        return warehouseDAO.getSuitablePlaces(width, height, depth);
    }
}
