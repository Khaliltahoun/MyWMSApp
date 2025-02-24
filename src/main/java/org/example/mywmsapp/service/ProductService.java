package org.example.mywmsapp.service;

import org.example.mywmsapp.dao.ProductDAO;
import org.example.mywmsapp.model.Product;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO = new ProductDAO();
    private final OdooService odooService = new OdooService();

    public Product getProductByBarcode(String barcode) {
        System.out.println("🔍 Recherche du produit avec le code-barres : " + barcode);

        // 1️⃣ Vérifier en base de données locale
        Product product = productDAO.getProductByBarcode(barcode);

        if (product != null) {
            System.out.println("✅ Produit trouvé en base de données : " + product.getName());
            return product;
        }

        // 2️⃣ Si non trouvé en DB, chercher sur Odoo
        System.out.println("⚠️ Produit non trouvé en DB. Recherche dans Odoo...");
        product = odooService.getProductByBarcode(barcode);

        if (product != null) {
            System.out.println("✅ Produit trouvé sur Odoo : " + product.getName());
            productDAO.insertProduct(product); // Enregistrer en DB
        } else {
            System.out.println("❌ Aucun produit trouvé pour ce code-barres !");
        }

        return product;
    }

    public boolean saveProduct(Product product) {
        return productDAO.insertProduct(product);
    }

    public List<Product> searchProducts(String query) {
        return productDAO.findProductsByNameOrBarcode(query);
    }

    public List<Product> searchProductsByBarcode(String barcode) {
        return productDAO.findProductByBarcode(barcode);
    }
}
