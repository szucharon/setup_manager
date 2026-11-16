
# GT3 Setup Manager - UML Class Diagram

```mermaid
classDiagram

class Track {
    -String name
    -double lengthKm
    +getName() String
    +getLengthKm() double
}

class Setup {
    -String carName
    -Track track
    -int rearWing
    -int tc
    -int abs
    -ArrayList~Double~ lapTimes
    +addLapTime(double lapTime) void
    +getLapTimes() ArrayList~Double~
    +getCarName() String
    +getTrack() Track
    +getRearWing() int
    +getTc() int
    +getAbs() int
}

class SetupManager {
    -ArrayList~Setup~ setups
    +addSetup(Setup setup) void
    +removeSetup(int number) void
    +getSetupCount() int
    +getSetup(int number) Setup
    +showSetups() void
    +compareSetups(int firstNumber, int secondNumber, ComparisonStrategy strategy) void
    +showSortedSetups(int number) void
}

class FileManager {
    -String fileName
    +saveSetups(SetupManager manager) void
    +loadSetups() SetupManager
}

class ComparisonStrategy {
    <<interface>>
    +calculate(Setup setup) double
}

class BestLapStrategy {
    +calculate(Setup setup) double
}

class AverageLapStrategy {
    +calculate(Setup setup) double
}

class InvalidSetupException {
    +InvalidSetupException(String message)
}

class Main {
    +main(String[] args) void
    +readNumber(Scanner scanner, String name, int min, int max) int
    +readLapTime(Scanner scanner) double
}

class RuntimeException

SetupManager "1" o-- "0..*" Setup : stores
Setup "0..*" --> "1" Track : uses

ComparisonStrategy <|.. BestLapStrategy
ComparisonStrategy <|.. AverageLapStrategy

SetupManager ..> ComparisonStrategy : compares
FileManager ..> SetupManager : saves and loads
Main ..> SetupManager : uses
Main ..> FileManager : uses

RuntimeException <|-- InvalidSetupException
Setup ..> InvalidSetupException : throws
```

## Design Pattern

A programban a Strategy (stratégia) tervezési mintát használom, amely a viselkedési tervezési minták közé tartozik.

A ComparisonStrategy interfész meghatározza a calculate() metódust, amelyet két osztály valósít meg:

BestLapStrategy: megkeresi a leggyorsabb köridőt.

AverageLapStrategy: kiszámítja a köridők átlagát.

A felhasználó a menüben kiválaszthatja, hogy melyik módszerrel szeretné összehasonlítani a setupokat.

A SetupManager a kiválasztott stratégia calculate() metódusát használja az összehasonlításhoz.

Ennek előnye, hogy a különböző számítási módszerek külön osztályokban találhatók, így nem kell minden módszerhez külön összehasonlító funkciót készíteni. A megoldás az interfészhasználatot és a polimorfizmust is bemutatja.

