package org.example.mywmsapp.service;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.example.mywmsapp.model.Product;

import java.net.URL;
import java.util.*;

public class OdooService {
    private static final String ODOO_URL = "https://agrifresh.odoo.com/";
    private static final String DB_NAME = "agrifresh";
    private static final String USERNAME = "ghitask216@gmail.com";
    private static final String PASSWORD = "AZERTYuiop123";

    private int userId;
    private XmlRpcClient commonClient;
    private XmlRpcClient objectClient;

    public OdooService() {
        try {
            if (USERNAME == null || PASSWORD == null) {
                throw new Exception("⚠️ Identifiants Odoo non configurés !");
            }

            XmlRpcClientConfigImpl commonConfig = new XmlRpcClientConfigImpl();
            commonConfig.setServerURL(new URL(ODOO_URL + "/xmlrpc/2/common"));

            commonClient = new XmlRpcClient();
            commonClient.setConfig(commonConfig);

            userId = (int) commonClient.execute("authenticate", Arrays.asList(DB_NAME, USERNAME, PASSWORD, new HashMap<>()));

            if (userId == 0) {
                throw new Exception("❌ Échec de l'authentification Odoo !");
            }

            XmlRpcClientConfigImpl objectConfig = new XmlRpcClientConfigImpl();
            objectConfig.setServerURL(new URL(ODOO_URL + "/xmlrpc/2/object"));
            objectClient = new XmlRpcClient();
            objectClient.setConfig(objectConfig);

            System.out.println("✅ Connexion à Odoo réussie !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Impossible de se connecter à Odoo !");
        }
    }

    /**
     * 🔍 Recherche un produit par son code-barres dans Odoo.
     */
    public Product getProductByBarcode(String barcode) {
        try {
            Object[] products = (Object[]) objectClient.execute("execute_kw", Arrays.asList(
                    DB_NAME, userId, PASSWORD,
                    "product.product", "search_read",
                    Arrays.asList(Arrays.asList(
                            Arrays.asList("barcode", "=", barcode)
                    )),
                    new HashMap<String, Object>() {{
                        put("fields", Arrays.asList("id", "name", "barcode", "qty_available", "categ_id"));
                        put("limit", 1);
                    }}
            ));

            if (products.length > 0) {
                Map<String, Object> productData = (Map<String, Object>) products[0];

                System.out.println("✅ Produit trouvé dans Odoo : " + productData);

                // 🔍 Extraction des données
                int productId = (int) productData.get("id");
                String productName = (String) productData.get("name");
                String productBarcode = (String) productData.get("barcode");

                // ✅ Correction : Assurer que la quantité est bien un `double`
                double quantity = 0.0;
                Object qtyObj = productData.get("qty_available");
                if (qtyObj instanceof Number) {
                    quantity = ((Number) qtyObj).doubleValue();
                }
                System.out.println("📦 Quantité disponible : " + quantity);

                // ✅ Correction : Extraire correctement le nom de la catégorie
                Object categIdRaw = productData.get("categ_id");
                String categoryName = extractCategoryName(categIdRaw);
                int categoryId = mapCategoryNameToId(categoryName);

                System.out.println("🎯 Catégorie extraite : " + categoryName + " (ID: " + categoryId + ")");

                return new Product(
                        productId, productName, productBarcode,
                        0.0, 0.0, 0.0,  // Valeurs par défaut pour les dimensions
                        quantity, categoryId, 5
                );
            } else {
                System.out.println("❌ Aucun produit trouvé avec le code-barres " + barcode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 🏷️ Extrait correctement le nom de la catégorie depuis Odoo.
     */
    private static String extractCategoryName(Object categIdRaw) {
        if (categIdRaw == null) {
            System.out.println("⚠️ `categ_id` est NULL. Catégorie par défaut utilisée.");
            return "Inconnu"; // Catégorie par défaut
        }

        System.out.println("🔍 Données brutes de la catégorie : " + categIdRaw);

        if (categIdRaw instanceof Object[] categoryData && categoryData.length > 1) {
            Object categoryName = categoryData[1];

            if (categoryName instanceof String) {
                return (String) categoryName; // ✅ Extraction correcte du nom
            } else {
                System.out.println("⚠️ `categ_id` ne contient pas de nom de catégorie.");
            }
        }

        System.out.println("⚠️ `categ_id` est invalide. Catégorie par défaut utilisée.");
        return "Inconnu"; // Catégorie par défaut si extraction échoue
    }

    /**
     * 🔄 Convertit le nom de la catégorie en un ID numérique.
     */
    private int mapCategoryNameToId(String categoryName) {
        switch (categoryName.toLowerCase()) {
            case "catégorie 1": return 1;
            case "catégorie 2": return 2;
            case "catégorie 3": return 3;
            case "catégorie 4": return 4;
            default: return -1; // Catégorie inconnue
        }
    }

    /**
     * 🔬 Test principal pour vérifier la récupération des produits.
     */
    public static void main(String[] args) {
        OdooService odooService = new OdooService();

        // 🔎 Tester avec différents codes-barres
        String barcode = "121212";  // Modifier pour tester d'autres produits
        Product product = odooService.getProductByBarcode(barcode);

        if (product != null) {
            System.out.println("✅ Produit trouvé : " + product.getName());
            System.out.println("📦 Quantité disponible : " + product.getQuantity());
            System.out.println("🏷️ Catégorie ID : " + product.getCategory());
        } else {
            System.out.println("❌ Aucun produit trouvé pour ce code-barres !");
        }
    }
}
