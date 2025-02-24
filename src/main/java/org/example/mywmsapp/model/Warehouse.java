package org.example.mywmsapp.model;

import java.util.List;

public class Warehouse {
    private int id;
    private String name;
    private String location;
    private List<Place> places;

    public Warehouse() {}

    public Warehouse(int id, String name, String location, List<Place> places) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.places = places;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<Place> getPlaces() { return places; }
    public void setPlaces(List<Place> places) { this.places = places; }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", places=" + places +
                '}';
    }
}
