package org.example.mywmsapp.model;

public class Place {
    private int id;
    private String locationCode;
    private double maxWidth;
    private double maxHeight;
    private double maxDepth;
    private boolean isOccupied;

    public Place() {}

    public Place(int id, String locationCode, double maxWidth, double maxHeight, double maxDepth, boolean isOccupied) {
        this.id = id;
        this.locationCode = locationCode;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.maxDepth = maxDepth;
        this.isOccupied = isOccupied;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLocationCode() { return locationCode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }

    public double getMaxWidth() { return maxWidth; }
    public void setMaxWidth(double maxWidth) { this.maxWidth = maxWidth; }

    public double getMaxHeight() { return maxHeight; }
    public void setMaxHeight(double maxHeight) { this.maxHeight = maxHeight; }

    public double getMaxDepth() { return maxDepth; }
    public void setMaxDepth(double maxDepth) { this.maxDepth = maxDepth; }

    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }

    // Ajout des méthodes demandées
    public String getName() {
        return "Emplacement " + locationCode;
    }

    public int getCapacity() {
        // Supposons que la capacité est déterminée par le volume de l'emplacement
        return (int) (maxWidth * maxHeight * maxDepth);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", locationCode='" + locationCode + '\'' +
                ", maxWidth=" + maxWidth +
                ", maxHeight=" + maxHeight +
                ", maxDepth=" + maxDepth +
                ", isOccupied=" + isOccupied +
                '}';
    }
}
