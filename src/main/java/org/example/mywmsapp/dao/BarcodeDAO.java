package org.example.mywmsapp.dao;

import org.example.mywmsapp.model.BarcodeScan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class BarcodeDAO {

    public boolean saveScan(String barcode, int warehouseId) {
        String sql = "INSERT INTO barcode_scans (barcode, scan_date, warehouse_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barcode);
            stmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            stmt.setInt(3, warehouseId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
