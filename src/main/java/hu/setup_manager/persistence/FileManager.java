package hu.setup_manager.persistence;

import hu.setup_manager.model.Setup;
import hu.setup_manager.model.Track;
import hu.setup_manager.model.InvalidSetupException;
import hu.setup_manager.service.SetupManager;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private final String fileName = "setups.txt";

    public void saveSetups(SetupManager manager) throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            writer.write(String.valueOf(manager.getSetupCount()));
            writer.newLine();

            for (int i = 1; i <= manager.getSetupCount(); i++) {

                Setup setup = manager.getSetup(i);

                writer.write(setup.getCarName());
                writer.newLine();

                writer.write(setup.getTrack().getName());
                writer.newLine();

                writer.write(String.valueOf(
                        setup.getTrack().getLengthKm()));
                writer.newLine();

                writer.write(String.valueOf(setup.getRearWing()));
                writer.newLine();

                writer.write(String.valueOf(setup.getTc()));
                writer.newLine();

                writer.write(String.valueOf(setup.getAbs()));
                writer.newLine();

                ArrayList<Double> lapTimes = setup.getLapTimes();

                writer.write(String.valueOf(lapTimes.size()));
                writer.newLine();

                for (double lapTime : lapTimes) {
                    writer.write(String.valueOf(lapTime));
                    writer.newLine();
                }
            }
        }
    }

    public SetupManager loadSetups() throws IOException {

        SetupManager manager = new SetupManager();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            int setupCount = Integer.parseInt(readLine(reader));

            if (setupCount < 0) {
                throw new IOException("Hibas setup darabszam!");
            }

            for (int i = 0; i < setupCount; i++) {

                String carName = readLine(reader);
                String trackName = readLine(reader);

                double lengthKm = Double.parseDouble(readLine(reader));
                int rearWing = Integer.parseInt(readLine(reader));
                int tc = Integer.parseInt(readLine(reader));
                int abs = Integer.parseInt(readLine(reader));

                int lapCount = Integer.parseInt(readLine(reader));

                if (lapCount < 0) {
                    throw new IOException("Hibas kor darabszam!");
                }

                Track track = new Track(trackName, lengthKm);
                Setup setup = new Setup(
                        carName, track, rearWing, tc, abs);

                for (int j = 0; j < lapCount; j++) {
                    double lapTime = Double.parseDouble(readLine(reader));
                    setup.addLapTime(lapTime);
                }

                manager.addSetup(setup);
            }

        } catch (NumberFormatException | InvalidSetupException e) {
            throw new IOException("Hibas adatok a mentesi fajlban!", e);
        }

        return manager;
    }

    private String readLine(BufferedReader reader) throws IOException {

        String line = reader.readLine();

        if (line == null) {
            throw new IOException("Hianyos mentesi fajl!");
        }

        return line;
    }
}

