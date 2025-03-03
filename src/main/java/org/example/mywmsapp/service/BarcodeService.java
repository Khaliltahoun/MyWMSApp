package org.example.mywmsapp.service;


import org.example.mywmsapp.dao.BarcodeDAO;
import org.example.mywmsapp.dao.ProductDAO;
import org.example.mywmsapp.model.Place;
import org.example.mywmsapp.model.Product;
import org.example.mywmsapp.model.Section;
import java.util.List;

public class BarcodeService {
    private static final ProductService productService = new ProductService();
    private static final WarehouseService warehouseService = new WarehouseService();
    private final SectionService sectionService = new SectionService();
    private static final ProductDAO productDAO = new ProductDAO();
    private static final BarcodeDAO barcodeDAO = new BarcodeDAO();

    // ✅ Vérifier si le code-barres est valide
    private static boolean validateBarcode(String barcode) {
        return barcode != null && !barcode.trim().isEmpty();
    }

    // ✅ Recherche du produit et des emplacements après scan
    public List<Place> processBarcodeScan(String barcode) {
        if (!validateBarcode(barcode)) {
            System.out.println("❌ Code-barres invalide !");
            return null;
        }

        // 🔍 Récupérer le produit
        Product product = productService.getProductByBarcode(barcode);
        if (product == null) {
            System.out.println("❌ Produit non trouvé pour ce code-barres !");
            return null;
        }

        // 🔎 Récupérer la section associée à la catégorie du produit
        Section section = sectionService.getSectionByCategory(product.getCategory());
        if (section == null) {
            System.out.println("⚠️ Aucune section trouvée pour la catégorie " + product.getCategory());
            return null;
        }

        System.out.println("📌 Produit de catégorie " + product.getCategory() + " -> Stockage dans la section " + section.getName());

        // 🔍 Trouver les places disponibles dans cette section
        return warehouseService.getAvailablePlacesForCategory(section.getCategory());
    }

    // ✅ Recherche d'un produit par code-barres
    public static Product scanProduct(String barcode) {
        if (!validateBarcode(barcode)) {
            System.out.println("❌ Code-barres invalide !");
            return null;
        }
        return productDAO.getProductByBarcode(barcode);
    }

    // ✅ Traitement du code-barres scanné
    public Product processBarcode(String barcode) {
        if (!validateBarcode(barcode)) {
            System.out.println("❌ Code-barres invalide !");
            return null;
        }

        System.out.println("🔎 Scanne du produit : " + barcode);
        Product product = productService.getProductByBarcode(barcode);
        if (product != null) {
            System.out.println("✅ Produit trouvé : " + product.getName() + " | Catégorie : " + product.getCategory());
        } else {
            System.out.println("⚠️ Aucun produit trouvé pour ce code-barres.");
        }
        return product;
    }

    // ✅ Récupérer l'ID de l'entrepôt en fonction de la section
    private static int getWarehouseIdForSection(int sectionId) {
        // ⚠️ À remplacer par une requête SQL si nécessaire
        return 1; // Par défaut, ID de l'entrepôt fixé à 1
    }

    /**
     * ✅ Saves a product into the database if it does not already exist.
     * @param product The product to save
     * @return true if the product was successfully saved, false otherwise
     */
    public boolean saveProduct(Product product) {
        if (product == null) {
            System.out.println("❌ [BarcodeService] Impossible de sauvegarder un produit NULL.");
            return false;
        }

        // 🔍 Vérifier si le produit existe déjà
        Product existingProduct = productDAO.getProductByBarcode(product.getBarcode());
        if (existingProduct != null) {
            System.out.println("✅ [BarcodeService] Produit déjà existant dans la DB: " + product.getName());
            return true; // Déjà présent, pas besoin de l'insérer
        }

        // 🔄 Insérer le nouveau produit
        boolean inserted = productDAO.insertProduct(product);
        if (inserted) {
            System.out.println("✅ [BarcodeService] Produit sauvegardé avec succès: " + product.getName());
        } else {
            System.out.println("❌ [BarcodeService] Échec de l'enregistrement du produit: " + product.getName());
        }
        return inserted;
    }

    // ✅ Stocker automatiquement un produit après scan
    public static boolean storeProduct(String barcode, int placeId, int sectionId) {
        if (!validateBarcode(barcode)) {
            System.out.println("❌ Code-barres invalide !");
            return false;
        }

        System.out.println("📦 Tentative de stockage du produit avec code-barres : " + barcode);

        // 🔍 Vérifier si le produit existe
        Product product = productService.getProductByBarcode(barcode);
        if (product == null) {
            System.out.println("❌ Impossible de stocker : Produit non trouvé.");
            return false;
        }

        // ✅ Mettre à jour la base de données pour indiquer que la place est occupée
        boolean stored = warehouseService.storeProduct(barcode, placeId);
        if (stored) {
            System.out.println("✅ Produit stocké à l’emplacement ID : " + placeId);
            int warehouseId = getWarehouseIdForSection(sectionId);
            barcodeDAO.saveScan(barcode, warehouseId, sectionId, placeId);
            return true;
        } else {
            System.out.println("❌ Échec du stockage du produit.");
            return false;
        }
    }
}
