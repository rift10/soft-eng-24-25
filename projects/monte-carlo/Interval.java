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

    public UniformRandomVariable uniformRandomWithInterval() {
        var x = (getRange() / 0.9 - getRange()) / 2;
        return new UniformRandomVariable(getLowerBound() - x, getUpperBound() + x);
    }

    public NormalRandomVariable normalRandomWithInterval() {
        return new NormalRandomVariable((getLowerBound() + getUpperBound()) / 2,
                ((getUpperBound() - getLowerBound()) / 2) / 1.645);
    }

    public LogNormalRandomVariable logNormalRandomWithInterval() {
        return new LogNormalRandomVariable(getLowerBound(), getUpperBound());
    }
}
