package org.example.mywmsapp.dao;

import org.example.mywmsapp.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public Product getProductByBarcode(String barcode) {
        String sql = "SELECT * FROM products WHERE barcode = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barcode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("barcode"),
                        rs.getDouble("width"),
                        rs.getDouble("height"),
                        rs.getDouble("depth"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertProduct(Product product) {
        String sql = "INSERT INTO products (name, barcode, width, height, depth, quantity) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getBarcode());
            stmt.setDouble(3, product.getWidth());
            stmt.setDouble(4, product.getHeight());
            stmt.setDouble(5, product.getDepth());
            stmt.setInt(6, product.getQuantity());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Product> findProductsByNameOrBarcode(String query) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR barcode LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("barcode"),
                        rs.getDouble("width"),
                        rs.getDouble("height"),
                        rs.getDouble("depth"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // 🔹 New method: Find products by barcode
    public List<Product> findProductByBarcode(String barcode) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE barcode = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barcode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("barcode"),
                        rs.getDouble("width"),
                        rs.getDouble("height"),
                        rs.getDouble("depth"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
