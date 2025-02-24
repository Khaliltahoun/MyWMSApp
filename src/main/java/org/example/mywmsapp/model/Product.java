package org.example.mywmsapp.model;

public class Product {
    private int id;
    private String name;
    private String barcode;
    private double width;
    private double height;
    private double depth;
    private int quantity;

    public Product() {}

    public Product(int id, String name, String barcode, double width, double height, double depth, int quantity) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.quantity = quantity;
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

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

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
                '}';
    }
}
