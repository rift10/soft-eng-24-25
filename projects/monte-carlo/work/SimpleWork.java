package work;

import randomvariables.LogNormalRandomVariable;

public class SimpleWork implements Work {

    private final double mean, stdev;

    public SimpleWork(double mean, double stdev) {
        this.mean = mean;
        this.stdev = stdev;
    }

    @Override
    public double generateEndTime() {
        return Math.log(new LogNormalRandomVariable(mean, stdev).next());
    }
}
