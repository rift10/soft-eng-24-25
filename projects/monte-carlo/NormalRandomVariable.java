import java.util.random.RandomGenerator;

public class NormalRandomVariable implements RandomVariable {

    private final double mean, stdev;
    private final RandomGenerator r = RandomGenerator.of("L128X1024MixRandom");

    public NormalRandomVariable(double mean, double stdev) {
        this.mean = mean;
        this.stdev = stdev;
    }

    @Override
    public double next() {
        return r.nextGaussian(mean, stdev);
    }
    
}
