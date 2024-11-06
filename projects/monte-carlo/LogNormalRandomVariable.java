import java.util.random.RandomGenerator;

public class LogNormalRandomVariable implements RandomVariable {

    private final double mean, stdev;
    private final RandomGenerator r = RandomGenerator.of("L128X1024MixRandom");

    public LogNormalRandomVariable(double mean, double stdev) {
        this.mean = mean;
        this.stdev = stdev;
    }

    @Override
    public double next() {
        return Math.exp(r.nextGaussian(mean, stdev));
    }
    
}
