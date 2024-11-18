import randomvariables.LogNormalRandomVariable;
import randomvariables.NormalRandomVariable;
import randomvariables.UniformRandomVariable;

/**
 * represents 90% prediction interval
 */
public class Interval {

    private final double lowerBound, upperBound;

    public Interval(double min, double max) {
        this.lowerBound = min;
        this.upperBound = max;
    }
    
    @Override
    public String toString() {
        return "lower bound: "  + lowerBound + ", upper bound: " + upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public double getRange() {
        return upperBound - lowerBound;
    }

    public static UniformRandomVariable uniformRandomWithInterval(Interval interval) {
        var x = (interval.getRange() / 0.9 - interval.getRange()) / 2;
        return new UniformRandomVariable(interval.getLowerBound() - x, interval.getUpperBound() + x);
    }

    public static NormalRandomVariable normalRandomWithInterval(Interval interval) {
        return new NormalRandomVariable((interval.getLowerBound() + interval.getUpperBound()) / 2,
                ((interval.getUpperBound() - interval.getLowerBound()) / 2) / 1.645);
    }

    public static LogNormalRandomVariable logNormalRandomWithInterval(Interval interval) {
        return new LogNormalRandomVariable(interval.getLowerBound(), interval.getUpperBound());
    }
}
