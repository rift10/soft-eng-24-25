package randomvariables;
import java.util.stream.DoubleStream;

public interface RandomVariable {

  /**
   * Generate the next random value.
   */
  public double next();

  /**
   * Generate a stream of random values.
   */
  public default DoubleStream stream() {
    return DoubleStream.generate(this::next);
  }
}
