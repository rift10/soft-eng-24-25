package randomvariables;
import java.util.random.RandomGenerator;

public class UniformRandomVariable implements RandomVariable {

    private final double min, max;
    private final RandomGenerator r = RandomGenerator.of("L128X1024MixRandom");

    public UniformRandomVariable(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public double next() {
        return r.nextDouble(min, max);
    }
    
}
