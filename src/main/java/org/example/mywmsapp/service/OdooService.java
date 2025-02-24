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
            XmlRpcClientConfigImpl commonConfig = new XmlRpcClientConfigImpl();
            commonConfig.setServerURL(new URL(ODOO_URL + "/xmlrpc/2/common"));

            commonClient = new XmlRpcClient();
            commonClient.setConfig(commonConfig);

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

    public Product getProductByBarcode(String barcode) {
        try {
            Object[] products = (Object[]) objectClient.execute("execute_kw", Arrays.asList(
                    DB_NAME, userId, PASSWORD,
                    "product.product", "search_read",
                    Arrays.asList(Arrays.asList(
                            Arrays.asList("barcode", "=", barcode)
                    )),
                    new HashMap<String, Object>() {{
                        put("fields", Arrays.asList("id", "name", "barcode", "qty_available")); // 🔥 Suppression des champs qui posent problème
                        put("limit", 1);
                    }}
            ));

            if (products.length > 0) {
                Map<String, Object> productData = (Map<String, Object>) products[0];

                System.out.println("✅ Produit trouvé dans Odoo : " + productData);

                return new Product(
                        (int) productData.get("id"),
                        (String) productData.get("name"),
                        (String) productData.get("barcode"),
                        0.0, // ⚠️ Valeur par défaut pour éviter NullPointerException
                        0.0,
                        0.0,
                        ((Number) productData.getOrDefault("qty_available", 0)).intValue()
                );
            } else {
                System.out.println("❌ Aucun produit trouvé avec le code-barres " + barcode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        OdooService odooService = new OdooService();
        System.out.println("Produit existe ? " + odooService.getProductByBarcode("123456789"));
    }
}
