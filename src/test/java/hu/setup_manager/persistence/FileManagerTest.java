package hu.setup_manager.persistence;

import hu.setup_manager.model.Setup;
import hu.setup_manager.model.Track;
import hu.setup_manager.service.SetupManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {

    @TempDir
    Path tempDir;

    @Test
    public void testSaveAndLoad() throws IOException {

        SetupManager manager = new SetupManager();

        Track track = new Track("Red Bull Ring", 4.318);
        Setup setup = new Setup("Ferrari 296 GT3",
                track, 4, 3, 3);

        setup.addLapTime(90.532);
        setup.addLapTime(91.124);

        manager.addSetup(setup);

        String fileName = tempDir.resolve("setups.txt").toString();
        FileManager fileManager = new FileManager(fileName);

        fileManager.saveSetups(manager);

        SetupManager loadedManager = fileManager.loadSetups();

        assertEquals(1, loadedManager.getSetupCount());

        Setup loadedSetup = loadedManager.getSetup(1);

        assertEquals("Ferrari 296 GT3", loadedSetup.getCarName());
        assertEquals("Red Bull Ring", loadedSetup.getTrack().getName());
        assertEquals(2, loadedSetup.getLapTimes().size());
        assertEquals(90.532, loadedSetup.getLapTimes().get(0), 0.001);
    }
}

