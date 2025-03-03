package org.example.mywmsapp.model;

public class Product {
    private int id;
    private String name;
    private String barcode;
    private double width;
    private double height;
    private double depth;
    private double quantity;
    private int category;  // 🔹 Nouvelle info : catégorie du produit
    private int minStockThreshold; // 🔹 Seuil d’alerte pour stock faible
    private Object rawCategoryData; // 🆕 Store raw data from Odoo


    public Product() {}

    public Product(int id, String name, String barcode, double width, double height, double depth, double quantity, int category, int minStockThreshold) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.quantity = quantity;
        this.category = category;
        this.minStockThreshold = minStockThreshold;
    }

    public Product(int id, String name, String barcode, int category, Object rawCategoryData) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.category = category;
        this.rawCategoryData = rawCategoryData;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getDepth() { return depth; }
    public void setDepth(double depth) { this.depth = depth; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public int getCategory() { return category; }  // 🔹 Getter pour la catégorie
    public void setCategory(int category) { this.category = category; }

    public int getMinStockThreshold() { return minStockThreshold; }  // 🔹 Getter pour le seuil d’alerte stock
    public void setMinStockThreshold(int minStockThreshold) { this.minStockThreshold = minStockThreshold; }

    // ✅ Getter for raw Odoo category data
    public Object getRawCategoryData() {
        return rawCategoryData;
    }

    // ✅ Setter for raw category data
    public void setRawCategoryData(Object rawCategoryData) {
        this.rawCategoryData = rawCategoryData;
    }

    // 🔹 Vérifie si le stock est inférieur au seuil
    public boolean isStockLow() {
        return quantity <= minStockThreshold;
    }







    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                ", quantity=" + quantity +
                ", category=" + category +  // 🔹 Affichage de la catégorie
                ", minStockThreshold=" + minStockThreshold +  // 🔹 Affichage du seuil de stock
                '}';
    }
}
