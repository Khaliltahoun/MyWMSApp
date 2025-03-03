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

            // 1️⃣.1 Vérifier si la catégorie correspond à celle de Odoo
            Product odooProduct = odooService.getProductByBarcode(barcode);

            if (odooProduct != null) {
                System.out.println("📦 Quantité Odoo trouvée : " + odooProduct.getQuantity());

                if (product != null) {
                    // If product exists in DB, update its quantity with Odoo's value
                    product.setQuantity(odooProduct.getQuantity());
                } else {
                    // If product is NOT in DB, use the Odoo product directly
                    product = odooProduct;
                }
            } else {
                System.out.println("❌ Produit non trouvé sur Odoo.");
            }
            if (odooProduct != null) {
                int odooCategory = extractCategoryId(odooProduct); // 🔹 Fix category extraction
                int dbCategory = product.getCategory();

                System.out.println("🔎 Catégorie actuelle (DB): " + dbCategory + " | Catégorie Odoo: " + odooCategory);

                if (odooCategory > 0 && odooCategory != dbCategory) {
                    System.out.println("🔄 Mise à jour de la catégorie du produit...");
                    try {
                        boolean updated = productDAO.updateProductCategory(barcode, odooCategory);
                        if (updated) {
                            product.setCategory(odooCategory);
                            System.out.println("✅ Catégorie mise à jour : " + odooCategory);
                        } else {
                            System.out.println("❌ Échec de la mise à jour en base.");
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Erreur lors de la mise à jour de la catégorie : " + e.getMessage());
                    }
                }
            }

            return product; // ✅ Retour du produit trouvé
        }

        // 2️⃣ Si non trouvé en DB, chercher sur Odoo
        System.out.println("⚠️ Produit non trouvé en DB. Recherche dans Odoo...");
        Product odooProduct = odooService.getProductByBarcode(barcode);

        if (odooProduct != null) {
            int odooCategory = extractCategoryId(odooProduct); // 🔹 Fix category extraction

            if (odooCategory > 0) {
                System.out.println("✅ Produit trouvé sur Odoo : " + odooProduct.getName());
                productDAO.insertProduct(odooProduct); // Enregistrer en DB
            } else {
                System.out.println("⚠️ Catégorie non valide récupérée depuis Odoo. Produit non enregistré.");
            }
        } else {
            System.out.println("❌ Aucun produit trouvé pour ce code-barres !");
        }

        return odooProduct;
    }

    /**
     * 🔹 Fix: Extracts the correct category ID from Odoo's response
     */
    private int extractCategoryId(Product odooProduct) {
        Object categoryData = odooProduct.getCategory(); // Adapt this to the real method

        if (categoryData instanceof Integer) {
            return (int) categoryData; // ✅ Direct integer case
        } else if (categoryData instanceof Object[] objArray && objArray.length > 0) {
            try {
                return Integer.parseInt(objArray[0].toString()); // ✅ Convert first element to int
            } catch (NumberFormatException e) {
                System.out.println("❌ Erreur de conversion de la catégorie: " + objArray[0]);
            }
        }

        System.out.println("⚠️ Catégorie invalide récupérée depuis Odoo: " + categoryData);
        return -1; // Default invalid category
    }

    /**
     * 🔄 Met à jour la catégorie d'un produit si elle est valide et différente
     */
    public boolean updateProductCategory(String barcode, int newCategory) {
        System.out.println("🔄 Mise à jour de la catégorie du produit avec code-barres : " + barcode);

        // Vérifier si le produit existe déjà dans la base
        Product existingProduct = productDAO.getProductByBarcode(barcode);
        if (existingProduct == null) {
            System.out.println("❌ Produit introuvable. Impossible de mettre à jour la catégorie.");
            return false;
        }

        // Vérifier si la catégorie est réellement différente
        if (existingProduct.getCategory() == newCategory) {
            System.out.println("✅ La catégorie est déjà correcte. Aucune mise à jour nécessaire.");
            return true;
        }

        // Effectuer la mise à jour en base
        boolean updated = productDAO.updateProductCategory(barcode, newCategory);
        if (updated) {
            System.out.println("✅ Catégorie mise à jour avec succès pour le produit : " + barcode);
        } else {
            System.out.println("❌ Échec de la mise à jour de la catégorie.");
        }

        return updated;
    }

    public boolean saveProduct(Product product) {
        return productDAO.insertProduct(product);
    }

    public boolean updateStock(String barcode, double quantity) {
        System.out.println("🔄 Mise à jour du stock pour le produit : " + barcode);
        return productDAO.updateProductStock(barcode, quantity);
    }

    public List<Product> searchProducts(String query) {
        return productDAO.findProductsByNameOrBarcode(query);
    }

    public List<Product> searchProductsByBarcode(String barcode) {
        return productDAO.findProductByBarcode(barcode);
    }
}
