package hu.setup_manager.model;

public class Track {

    private String name;
    private double lengthKm;

    public Track(String name, double lengthKm) {
        this.name = name;
        this.lengthKm = lengthKm;
    }

    public String getName() {
        return name;
    }

    public double getLengthKm() {
        return lengthKm;
    }
}

