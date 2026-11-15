package hu.setup_manager.strategy;

import hu.setup_manager.model.Setup;
import hu.setup_manager.model.Track;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategyTest {

    @Test
    public void testBestLap() {

        Track track = new Track("Red Bull Ring", 4.318);
        Setup setup = new Setup("Ferrari 296 GT3",
                track, 4, 3, 3);

        setup.addLapTime(90.5);
        setup.addLapTime(91.2);
        setup.addLapTime(89.8);

        BestLapStrategy strategy = new BestLapStrategy();

        assertEquals(89.8, strategy.calculate(setup), 0.001);
    }

    @Test
    public void testAverageLap() {

        Track track = new Track("Red Bull Ring", 4.318);
        Setup setup = new Setup("Ferrari 296 GT3",
                track, 4, 3, 3);

        setup.addLapTime(90.0);
        setup.addLapTime(92.0);

        AverageLapStrategy strategy = new AverageLapStrategy();

        assertEquals(91.0, strategy.calculate(setup), 0.001);
    }
}

