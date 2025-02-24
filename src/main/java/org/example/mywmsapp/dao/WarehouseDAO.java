package org.example.mywmsapp.dao;

import org.example.mywmsapp.model.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO {

    public List<Place> getAvailablePlaces() {
        List<Place> places = new ArrayList<>();
        String sql = "SELECT * FROM warehouse_places WHERE is_occupied = false";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                places.add(new Place(
                        rs.getInt("id"),
                        rs.getString("location_code"),
                        rs.getDouble("max_width"),
                        rs.getDouble("max_height"),
                        rs.getDouble("max_depth"),
                        rs.getBoolean("is_occupied")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return places;
    }

    // ✅ Ajoute cette méthode pour trouver les emplacements adaptés
    public List<Place> getSuitablePlaces(double width, double height, double depth) {
        List<Place> places = new ArrayList<>();
        String sql = "SELECT * FROM warehouse_places WHERE is_occupied = false " +
                "AND max_width >= ? AND max_height >= ? AND max_depth >= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, width);
            stmt.setDouble(2, height);
            stmt.setDouble(3, depth);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                places.add(new Place(
                        rs.getInt("id"),
                        rs.getString("location_code"),
                        rs.getDouble("max_width"),
                        rs.getDouble("max_height"),
                        rs.getDouble("max_depth"),
                        rs.getBoolean("is_occupied")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return places;
    }
}
