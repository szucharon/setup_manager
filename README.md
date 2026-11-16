# Setup Manager

## Projekt bemutatása

A Setup Manager egy Java nyelven készített, konzolos alkalmazás, amely versenyautók beállításainak (setupjainak) nyilvántartására szolgál.

A program elsősorban autóverseny-szimulátorokat használó játékosok számára készült. Segítségével különböző autókhoz és pályákhoz tartozó beállításokat lehet elmenteni, köridőket rögzíteni, valamint a setupok teljesítményét összehasonlítani.

## A program funkciói

A program az alábbi funkciókat tartalmazza:

1. **Új setup létrehozása:** autó neve, pálya neve és hossza, hátsó szárnyállás, TC és ABS megadása.
2. **Setupok megtekintése:** a létrehozott beállítások és a hozzájuk tartozó köridők listázása.
3. **Köridő hozzáadása:** egy setuphoz több köridő is rögzíthető.
4. **Setupok összehasonlítása:** két setup összehasonlítása a legjobb vagy az átlagos köridő alapján.
5. **Setup törlése:** egy kiválasztott setup eltávolítása megerősítés után.
6. **Setupok rendezése:** azonos pályán készült setupok rangsorolása a legjobb köridő alapján.
7. **Adatok mentése:** a setupok és köridők mentése szöveges fájlba.
8. **Adatok betöltése:** a korábban elmentett adatok visszatöltése.

## Felhasznált technológiák

- Java
- Maven
- JUnit 5
- Git és GitHub
- IntelliJ IDEA

A program konzolos felületen működik, grafikus felületet nem tartalmaz.

## A program futtatása

A program futtatásához a `pom.xml` fájlban megadott Java-verzióval kompatibilis JDK és Maven szükséges.

A projekt megnyitása után az IntelliJ IDEA-ban a `Main.java` fájl `main()` metódusával indítható.

Parancssorból a projekt főmappájában a következő Maven-paranccsal fordítható:

`mvn clean package`

Sikeres fordítás után a program a következő paranccsal indítható:

`java -cp target/classes hu.setup_manager.ui.Main`

A JUnit-tesztek külön is futtathatók:

`mvn test`

## Példa a használatra

Új setup létrehozásakor a felhasználó például ezeket az adatokat adhatja meg:

| Beállítás | Érték |
|---|---|
| Autó | Ferrari 296 GT3 |
| Pálya | Red Bull Ring |
| Pályahossz | 4318 méter |
| Hátsó szárny | 4 |
| TC | 3 |
| ABS | 3 |

A setuphoz ezután köridők rendelhetők, például `1:30.532` és `1:31.124`.

A program a köridőket másodpercben tárolja, és a megjelenítésnél visszaalakítja perc:másodperc formátumra.

Az összehasonlításhoz két különböző setupot kell kiválasztani. A program ellenőrzi, hogy a pálya neve és hossza megegyezik-e, valamint hogy mindkét setup rendelkezik-e köridővel.

## Objektumorientált megoldások

A program a következő OOP-alapelveket használja:

**Egységbezárás (Encapsulation)**

Az osztályok adattagjai `private` láthatóságúak. Az adatokhoz megfelelő metódusokon keresztül lehet hozzáférni.

**Absztrakció (Abstraction)**

A `ComparisonStrategy` interfész meghatározza a setupok köridő alapján történő értékeléséhez szükséges `calculate()` metódust.

**Öröklődés és polimorfizmus (Inheritance and Polymorphism)**

A `BestLapStrategy` és az `AverageLapStrategy` osztályok ugyanazt az interfészt valósítják meg, de eltérő módon számítják ki az eredményt.

**Osztályok közötti kapcsolatok**

A `Setup` osztály egy `Track` objektumot tartalmaz, a `SetupManager` pedig több `Setup` objektumot kezel.

**Kollekciók és rendezés**

A setupokat és köridőket `ArrayList` segítségével tároljuk. A setupok rendezése a legjobb köridő alapján történik.

**Kivételkezelés**

Az `InvalidSetupException` saját kivételosztály a hibás setupadatok kezelésére szolgál. A program a hibás felhasználói bemeneteket és a fájlkezelési hibákat is kezeli.

## Alkalmazott tervezési minta – Strategy

A program a **Strategy (stratégia) tervezési mintát** alkalmazza, amely a viselkedési tervezési minták közé tartozik.

A minta célja, hogy ugyanazt a feladatot különböző algoritmusokkal lehessen megoldani.

A megvalósításban három fontos elem szerepel:

- `ComparisonStrategy`: interfész, amely meghatározza a `calculate()` metódust.
- `BestLapStrategy`: megkeresi a leggyorsabb köridőt.
- `AverageLapStrategy`: kiszámítja az összes köridő átlagát.

A `SetupManager` a kiválasztott stratégiát használja az összehasonlításhoz.

A megoldás előnye, hogy a különböző számítási módszerek elkülönülnek egymástól. Új összehasonlítási módszer hozzáadásakor nem kell jelentősen módosítani a meglévő összehasonlító logikát.

## Adatok tárolása

Az adatok mentését és betöltését a `FileManager` osztály végzi.

A setupokat és a hozzájuk tartozó köridőket a program egy `setups.txt` nevű szöveges fájlba menti.

A mentés és a betöltés a főmenüből végezhető el.

A mentés kézi művelet, tehát a program nem ment automatikusan minden változtatás után.

## Tesztelés

A program JUnit segítségével készült automatikus teszteket tartalmaz.

A tesztek ellenőrzik:

- A setupok megfelelő létrehozását és a hibás adatok kezelését.
- A köridők tárolását.
- A legjobb köridő meghatározását.
- Az átlagos köridő kiszámítását.
- Az adatok mentését és visszatöltését.

A projekt összesen 6 JUnit-tesztet tartalmaz.

## Dokumentáció

Az UML-osztálydiagram és a Strategy tervezési minta magyarázata a `docs/class-diagram.md` fájlban található.

A felhasználói történetek a `docs/user-stories.md` fájlban szerepelnek.

## Ismert korlátozások

- A program kizárólag konzolos felületen működik.
- A setupbeállítások megengedett tartományai egyszerűsítettek: hátsó szárny 1–10, TC és ABS 0–10.
- A program nem végez fizikai szimulációt és nem számítja ki automatikusan a várható köridőt.
- Az autók és a pályák adatait a felhasználónak kell megadnia.
- A mentés nem automatikus.
- A setupok összehasonlításához azonos nevű és hosszúságú pályák szükségesek.

## Készítő

[Név] – egyéni Java OOP projektmunka, Programozás 2.