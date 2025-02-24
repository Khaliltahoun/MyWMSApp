package org.example.mywmsapp.service;

import org.example.mywmsapp.dao.ProductDAO;
import org.example.mywmsapp.model.Product;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO = new ProductDAO();

    public Product getProductByBarcode(String barcode) {
        return productDAO.getProductByBarcode(barcode);
    }

    public boolean saveProduct(Product product) {
        return productDAO.insertProduct(product);
    }


    public List<Product> searchProducts(String query) {
        return productDAO.findProductsByNameOrBarcode(query);
    }


    // Search products by barcode (new method)
    public List<Product> searchProductsByBarcode(String barcode) {
        return productDAO.findProductByBarcode(barcode);
    }

}
