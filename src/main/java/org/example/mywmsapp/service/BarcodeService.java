package org.example.mywmsapp.service;

import org.example.mywmsapp.dao.ProductDAO;
import org.example.mywmsapp.model.Product;
import org.example.mywmsapp.model.Place;
import java.util.List;

public class BarcodeService {
    private final ProductService productService = new ProductService();
    private final WarehouseService warehouseService = new WarehouseService();
    private final ProductDAO productDAO = new ProductDAO();

    public List<Place> processBarcodeScan(String barcode) {
        Product product = productService.getProductByBarcode(barcode);

        if (product == null) {
            return null;
        }

        return warehouseService.getAvailablePlacesForProduct(product.getWidth(), product.getHeight(), product.getDepth());
    }

    public Product scanProduct(String barcode) {
        return productDAO.getProductByBarcode(barcode);
    }


    public Product processBarcode(String barcode) {
        System.out.println("🔎 Processing barcode scan: " + barcode);

        Product product = productService.getProductByBarcode(barcode);

        if (product != null) {
            System.out.println("✅ Product retrieved: " + product.getName());
        } else {
            System.out.println("⚠️ No product found for barcode: " + barcode);
        }

        return product;
    }
}
