package hu.setup_manager.model;

import java.util.ArrayList;

public class Setup {

    private String carName;
    private Track track;
    private int rearWing;
    private int tc;
    private int abs;

    private ArrayList<Double> lapTimes;

    public Setup(String carName, Track track,
                 int rearWing, int tc, int abs) {

        if (carName == null || carName.isBlank()) {
            throw new InvalidSetupException("Az auto neve nem lehet ures!");
        }

        if (track == null) {
            throw new InvalidSetupException("Palya megadasa kotelezo!");
        }

        if (rearWing < 1 || rearWing > 10) {
            throw new InvalidSetupException("A hatso szarny 1 es 10 kozott lehet!");
        }

        if (tc < 0 || tc > 10) {
            throw new InvalidSetupException("A TC 0 es 10 kozott lehet!");
        }

        if (abs < 0 || abs > 10) {
            throw new InvalidSetupException("Az ABS 0 es 10 kozott lehet!");
        }

        this.carName = carName;
        this.track = track;
        this.rearWing = rearWing;
        this.tc = tc;
        this.abs = abs;

        lapTimes = new ArrayList<>();
    }

    public void addLapTime(double lapTime) {
        if (lapTime <= 0 || Double.isNaN(lapTime) || Double.isInfinite(lapTime)) {
            throw new InvalidSetupException("Ervenytelen korido!");
        }

        lapTimes.add(lapTime);
    }

    public ArrayList<Double> getLapTimes() {
        return new ArrayList<>(lapTimes);
    }

    public String getCarName() {
        return carName;
    }

    public Track getTrack() {
        return track;
    }

    public int getRearWing() {
        return rearWing;
    }

    public int getTc() {
        return tc;
    }

    public int getAbs() {
        return abs;
    }
}
