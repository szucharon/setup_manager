package hu.setup_manager.service;

import hu.setup_manager.model.Setup;
import hu.setup_manager.strategy.ComparisonStrategy;
import hu.setup_manager.strategy.BestLapStrategy;

import java.util.ArrayList;
import java.util.Locale;

public class SetupManager {

    private ArrayList<Setup> setups;

    public SetupManager() {
        setups = new ArrayList<>();
    }

    public void addSetup(Setup setup) {
        setups.add(setup);
    }

    public void removeSetup(int number) {
        setups.remove(number - 1);
    }

    public int getSetupCount() {
        return setups.size();
    }

    public Setup getSetup(int number) {
        return setups.get(number - 1);
    }

    public void showSetups() {

        if (setups.isEmpty()) {
            System.out.println("Meg nincs elmentett setup!");
            return;
        }

        for (int i = 0; i < setups.size(); i++) {

            Setup setup = setups.get(i);

            System.out.println("\n" + (i + 1) + ". setup");
            System.out.println("Auto: " + setup.getCarName());
            System.out.println("Palya: " + setup.getTrack().getName());
            System.out.println("Palya hossza: "
                    + setup.getTrack().getLengthKm() + " km");
            System.out.println("Rear Wing: " + setup.getRearWing());
            System.out.println("TC: " + setup.getTc());
            System.out.println("ABS: " + setup.getAbs());

            ArrayList<Double> lapTimes = setup.getLapTimes();

            if (lapTimes.isEmpty()) {
                System.out.println("Meg nincs korido.");
            } else {
                System.out.println("Koridok:");

                for (int j = 0; j < lapTimes.size(); j++) {
                    System.out.println((j + 1) + ". kor: "
                            + formatLapTime(lapTimes.get(j)));
                }
            }

            System.out.println("--------------------");
        }
    }

    public void compareSetups(int firstNumber, int secondNumber,
                              ComparisonStrategy strategy) {

        if (firstNumber == secondNumber) {
            System.out.println("Ket kulonbozo setupot valassz!");
            return;
        }

        Setup setup1 = getSetup(firstNumber);
        Setup setup2 = getSetup(secondNumber);

        String track1 = setup1.getTrack().getName().trim();
        String track2 = setup2.getTrack().getName().trim();

        if (!track1.equalsIgnoreCase(track2)
                || setup1.getTrack().getLengthKm()
                != setup2.getTrack().getLengthKm()) {

            System.out.println("A setupok nem ugyanahhoz a palyahoz tartoznak!");
            return;
        }

        if (setup1.getLapTimes().isEmpty()
                || setup2.getLapTimes().isEmpty()) {

            System.out.println("Mindket setuphoz kell legalabb egy korido!");
            return;
        }

        double result1 = strategy.calculate(setup1);
        double result2 = strategy.calculate(setup2);

        System.out.println("\n--- OSSZEHASONLITAS EREDMENYE ---");

        System.out.println("1. " + setup1.getCarName()
                + ": " + formatLapTime(result1));

        System.out.println("2. " + setup2.getCarName()
                + ": " + formatLapTime(result2));

        if (result1 < result2) {
            System.out.println("Gyorsabb setup: " + setup1.getCarName());
        } else if (result2 < result1) {
            System.out.println("Gyorsabb setup: " + setup2.getCarName());
        } else {
            System.out.println("A ket setup eredmenye megegyezik!");
        }
    }

    public void showSortedSetups(int referenceNumber) {

        Setup reference = getSetup(referenceNumber);

        ArrayList<Setup> sortedSetups = new ArrayList<>();

        for (Setup setup : setups) {

            boolean sameName = setup.getTrack().getName().trim()
                    .equalsIgnoreCase(reference.getTrack().getName().trim());

            boolean sameLength = setup.getTrack().getLengthKm()
                    == reference.getTrack().getLengthKm();

            if (sameName && sameLength && !setup.getLapTimes().isEmpty()) {
                sortedSetups.add(setup);
            }
        }

        if (sortedSetups.isEmpty()) {
            System.out.println("Ezen a palyan meg nincs korido!");
            return;
        }

        BestLapStrategy strategy = new BestLapStrategy();

        sortedSetups.sort((a, b) ->
                Double.compare(strategy.calculate(a),
                        strategy.calculate(b)));

        System.out.println("\n--- KORIDO RANGSOR ---");
        System.out.println("Palya: " + reference.getTrack().getName());

        for (int i = 0; i < sortedSetups.size(); i++) {

            Setup setup = sortedSetups.get(i);

            System.out.println((i + 1) + ". "
                    + setup.getCarName() + " - "
                    + formatLapTime(strategy.calculate(setup)));
        }
    }

    private String formatLapTime(double lapTime) {

        int minutes = (int) (lapTime / 60);
        double seconds = lapTime % 60;

        return String.format(Locale.US,
                "%d:%06.3f", minutes, seconds);
    }
}
