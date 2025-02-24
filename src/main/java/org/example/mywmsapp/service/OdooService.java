package org.example.mywmsapp.service;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.example.mywmsapp.model.Product;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            // Configuration du client XML-RPC
            XmlRpcClientConfigImpl commonConfig = new XmlRpcClientConfigImpl();
            commonConfig.setServerURL(new URL(ODOO_URL + "/xmlrpc/2/common"));

            commonClient = new XmlRpcClient();
            commonClient.setConfig(commonConfig);

            // Authentification avec Odoo
            userId = (int) commonClient.execute("authenticate", Arrays.asList(DB_NAME, USERNAME, PASSWORD, new HashMap<>()));

            if (userId == 0) {
                throw new Exception("Échec de l'authentification Odoo !");
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
    public boolean testConnection() {
        try {
            int userIdTest = (int) commonClient.execute("authenticate", Arrays.asList(DB_NAME, USERNAME, PASSWORD, new HashMap<>()));
            return userIdTest > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isProductExists(String barcode) {
        try {
            List<Object> products = Arrays.asList((Object[]) objectClient.execute("execute_kw", Arrays.asList(
                    DB_NAME, userId, PASSWORD,
                    "product.product", "search",
                    Arrays.asList(Arrays.asList(
                            Arrays.asList("barcode", "=", barcode)
                    ))
            )));
            return !products.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    // Récupérer un produit par code-barres
    public Product getProductByBarcode(String barcode) {
        try {
            List<Object> products = Arrays.asList((Object[]) objectClient.execute("execute_kw", Arrays.asList(
                    DB_NAME, userId, PASSWORD,
                    "product.product", "search_read",
                    Arrays.asList(Arrays.asList(
                            Arrays.asList("barcode", "=", barcode)
                    )),
                    new HashMap<String, Object>() {{
                        put("fields", Arrays.asList("id", "name", "barcode", "width", "height", "depth", "qty_available"));
                        put("limit", 1);
                    }}
            )));

            if (!products.isEmpty()) {
                Map<String, Object> productData = (Map<String, Object>) products.get(0);
                return new Product(
                        (int) productData.get("id"),
                        (String) productData.get("name"),
                        (String) productData.get("barcode"),
                        (double) productData.getOrDefault("width", 0.0),
                        (double) productData.getOrDefault("height", 0.0),
                        (double) productData.getOrDefault("depth", 0.0),
                        (int) productData.getOrDefault("qty_available", 0)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Ajouter un nouveau produit dans Odoo
    public boolean createProduct(Product product) {
        try {
            int newProductId = (int) objectClient.execute("execute_kw", Arrays.asList(
                    DB_NAME, userId, PASSWORD,
                    "product.product", "create",
                    Arrays.asList(new HashMap<String, Object>() {{
                        put("name", product.getName());
                        put("barcode", product.getBarcode());
                        put("width", product.getWidth());
                        put("height", product.getHeight());
                        put("depth", product.getDepth());
                        put("qty_available", product.getQuantity());
                    }})
            ));

            return newProductId > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        OdooService odooService = new OdooService();
        if (odooService.testConnection()) {
            System.out.println("✅ Connexion à Odoo réussie !");
        } else {
            System.out.println("❌ Échec de la connexion à Odoo !");
        }
        System.out.println("Produit existe ? " + odooService.isProductExists("123456789"));
    }

}
