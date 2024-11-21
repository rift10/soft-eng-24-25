package work;

import randomvariables.LogNormalRandomVariable;

public class Work {

    private final double mean, stdev;

    public Work(double mean, double stdev) {
        this.mean = mean;
        this.stdev = stdev;
    }

    public double generateEndTime() {
        return Math.log(new LogNormalRandomVariable(mean, stdev).next());
    }

}