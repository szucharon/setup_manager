package hu.setup_manager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetupTest {

    @Test
    public void testValidSetup() {

        Track track = new Track("Red Bull Ring", 4.318);

        Setup setup = new Setup("Ferrari 296 GT3",
                track, 4, 3, 3);

        assertEquals("Ferrari 296 GT3", setup.getCarName());
        assertEquals(4, setup.getRearWing());
        assertEquals(3, setup.getTc());
        assertEquals(3, setup.getAbs());
    }

    @Test
    public void testInvalidRearWing() {

        Track track = new Track("Red Bull Ring", 4.318);

        assertThrows(InvalidSetupException.class, () -> {
            new Setup("Ferrari 296 GT3", track, 15, 3, 3);
        });
    }

    @Test
    public void testAddLapTime() {

        Track track = new Track("Red Bull Ring", 4.318);

        Setup setup = new Setup("Ferrari 296 GT3",
                track, 4, 3, 3);

        setup.addLapTime(90.532);
        setup.addLapTime(91.124);

        assertEquals(2, setup.getLapTimes().size());
    }
}
