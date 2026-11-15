package hu.setup_manager.strategy;

import hu.setup_manager.model.Setup;
import java.util.ArrayList;

public class AverageLapStrategy implements ComparisonStrategy {

    @Override
    public double calculate(Setup setup) {

        ArrayList<Double> lapTimes = setup.getLapTimes();

        double sum = 0;

        for (double lapTime : lapTimes) {
            sum += lapTime;
        }

        return sum / lapTimes.size();
    }
}

