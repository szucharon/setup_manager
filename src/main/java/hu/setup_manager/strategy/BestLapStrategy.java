package hu.setup_manager.strategy;

import hu.setup_manager.model.Setup;
import java.util.ArrayList;

public class BestLapStrategy implements ComparisonStrategy {

    @Override
    public double calculate(Setup setup) {

        ArrayList<Double> lapTimes = setup.getLapTimes();

        double best = lapTimes.get(0);

        for (double lapTime : lapTimes) {
            if (lapTime < best) {
                best = lapTime;
            }
        }

        return best;
    }
}

