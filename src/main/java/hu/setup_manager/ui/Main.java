
package hu.setup_manager.ui;

import hu.setup_manager.model.Track;
import hu.setup_manager.model.Setup;
import hu.setup_manager.service.SetupManager;
import hu.setup_manager.persistence.FileManager;
import hu.setup_manager.strategy.ComparisonStrategy;
import hu.setup_manager.strategy.BestLapStrategy;
import hu.setup_manager.strategy.AverageLapStrategy;

import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SetupManager manager = new SetupManager();
        FileManager fileManager = new FileManager();

        boolean running = true;

        while (running) {

            System.out.println("\n===== GT3 SETUP MANAGER =====");
            System.out.println("1. Uj setup letrehozasa");
            System.out.println("2. Setupok megtekintese");
            System.out.println("3. Korido hozzaadasa");
            System.out.println("4. Setupok osszehasonlitasa");
            System.out.println("5. Setup torlese");
            System.out.println("6. Setupok rendezese korido alapjan");
            System.out.println("7. Setupok mentese");
            System.out.println("8. Setupok betoltese");
            System.out.println("0. Kilepes");
            System.out.print("\nValasztas: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    System.out.println("\n--- UJ SETUP LETREHOZASA ---");

                    System.out.print("\nAuto neve: ");
                    String carName = scanner.nextLine();

                    while (carName.isBlank()) {
                        System.out.print("Az auto neve nem lehet ures! Auto neve: ");
                        carName = scanner.nextLine();
                    }

                    System.out.print("Palya neve: ");
                    String trackName = scanner.nextLine();

                    while (trackName.isBlank()) {
                        System.out.print("A palya neve nem lehet ures! Palya neve: ");
                        trackName = scanner.nextLine();
                    }

                    int lengthMeters = readNumber(scanner,
                            "Palya hossza meterben", 1000, 30000);

                    double lengthKm = lengthMeters / 1000.0;

                    System.out.println("\n--- AUTO BEALLITASAI ---");

                    int rearWing = readNumber(scanner,
                            "Hatso szarny", 1, 10);

                    int tc = readNumber(scanner,
                            "Traction Control (TC)", 0, 10);

                    int abs = readNumber(scanner,
                            "ABS", 0, 10);

                    Track track = new Track(trackName, lengthKm);
                    Setup setup = new Setup(carName, track, rearWing, tc, abs);

                    manager.addSetup(setup);

                    System.out.println("\nSetup sikeresen letrehozva!");
                    break;

                case "2":
                    System.out.println("\n--- MENTETT SETUPOK ---");
                    manager.showSetups();
                    break;

                case "3":
                    System.out.println("\n--- KORIDO HOZZAADASA ---");

                    if (manager.getSetupCount() == 0) {
                        System.out.println("Meg nincs setup!");
                        break;
                    }

                    manager.showSetups();

                    int setupNumber = readNumber(scanner,
                            "Valaszd ki a setupot",
                            1, manager.getSetupCount());

                    double lapTime = readLapTime(scanner);

                    Setup selectedSetup = manager.getSetup(setupNumber);
                    selectedSetup.addLapTime(lapTime);

                    System.out.println("\nKorido sikeresen hozzaadva!");
                    break;

                case "4":
                    System.out.println("\n--- SETUPOK OSSZEHASONLITASA ---");

                    if (manager.getSetupCount() < 2) {
                        System.out.println("Legalabb ket setup szukseges!");
                        break;
                    }

                    manager.showSetups();

                    int firstNumber = readNumber(scanner,
                            "Elso setup sorszama",
                            1, manager.getSetupCount());

                    int secondNumber = readNumber(scanner,
                            "Masodik setup sorszama",
                            1, manager.getSetupCount());

                    System.out.println("\nOsszehasonlitas modja:");
                    System.out.println("1. Legjobb korido");
                    System.out.println("2. Atlagos korido");

                    int mode = readNumber(scanner,
                            "Valasztas", 1, 2);

                    ComparisonStrategy strategy;

                    if (mode == 1) {
                        strategy = new BestLapStrategy();
                        System.out.println("\nLegjobb korido alapjan:");
                    } else {
                        strategy = new AverageLapStrategy();
                        System.out.println("\nAtlagos korido alapjan:");
                    }

                    manager.compareSetups(firstNumber, secondNumber, strategy);
                    break;

                case "5":
                    System.out.println("\n--- SETUP TORLESE ---");

                    if (manager.getSetupCount() == 0) {
                        System.out.println("Nincs torolheto setup!");
                        break;
                    }

                    manager.showSetups();

                    int deleteNumber = readNumber(scanner,
                            "Torolni kivant setup",
                            1, manager.getSetupCount());

                    System.out.print("Biztosan torolni szeretned? (i/n): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("i")) {
                        manager.removeSetup(deleteNumber);
                        System.out.println("Setup sikeresen torolve!");
                    } else {
                        System.out.println("Torles megszakitva.");
                    }

                    break;

                case "6":
                    System.out.println("\n--- SETUPOK RENDEZESE ---");

                    if (manager.getSetupCount() == 0) {
                        System.out.println("Meg nincs setup!");
                        break;
                    }

                    manager.showSetups();

                    int referenceNumber = readNumber(scanner,
                            "Valassz egy setupot a palya kivalasztasahoz",
                            1, manager.getSetupCount());

                    manager.showSortedSetups(referenceNumber);
                    break;

                case "7":
                    System.out.println("\n--- SETUPOK MENTESE ---");

                    try {
                        fileManager.saveSetups(manager);
                        System.out.println("Setupok sikeresen elmentve!");
                    } catch (IOException e) {
                        System.out.println("Hiba a mentes soran: "
                                + e.getMessage());
                    }

                    break;

                case "8":
                    System.out.println("\n--- SETUPOK BETOLTESE ---");

                    System.out.println("A betoltes lecsereli a jelenlegi setupokat!");
                    System.out.print("Folytatod? (i/n): ");

                    String loadConfirm = scanner.nextLine();

                    if (!loadConfirm.equalsIgnoreCase("i")) {
                        System.out.println("Betoltes megszakitva.");
                        break;
                    }

                    try {
                        manager = fileManager.loadSetups();
                        System.out.println("Setupok sikeresen betoltve!");
                    } catch (FileNotFoundException e) {
                        System.out.println("Meg nincs mentett fajl!");
                    } catch (IOException e) {
                        System.out.println("Hiba a betoltes soran: "
                                + e.getMessage());
                    }

                    break;

                case "0":
                    running = false;
                    System.out.println("\nKilepes a programbol...");
                    break;

                default:
                    System.out.println("\nHibas valasztas! Probald ujra.");
            }
        }

        scanner.close();
    }

    public static int readNumber(Scanner scanner, String name,
                                 int min, int max) {

        while (true) {

            System.out.print(name + " (" + min + "-" + max + "): ");

            try {
                int number = Integer.parseInt(scanner.nextLine());

                if (number >= min && number <= max) {
                    return number;
                }

                System.out.println("Hibas ertek! Adj meg egy szamot "
                        + min + " es " + max + " kozott!");

            } catch (NumberFormatException e) {
                System.out.println("Hiba! Egesz szamot adj meg!");
            }
        }
    }

    public static double readLapTime(Scanner scanner) {

        while (true) {

            System.out.print("Korido (pl. 1:30.532): ");
            String input = scanner.nextLine();

            String[] parts = input.split(":");

            if (parts.length != 2) {
                System.out.println("Hibas formatum! Pelda: 1:30.532");
                continue;
            }

            try {
                int minutes = Integer.parseInt(parts[0]);
                double seconds = Double.parseDouble(parts[1]);

                if (minutes >= 0 && seconds >= 0 && seconds < 60
                        && (minutes > 0 || seconds > 0)) {

                    return minutes * 60 + seconds;
                }

                System.out.println("Ervenytelen korido!");

            } catch (NumberFormatException e) {
                System.out.println("Hibas formatum! Pelda: 1:30.532");
            }
        }
    }
}
