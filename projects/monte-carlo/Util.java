
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static double mean(double x, double y) {
        return (x + y) / 2;
    }

    public static double mean(List<Double> summands) {
        return summands.stream().collect(Collectors.summingDouble(x -> x)) / summands.size();
    }

    public static double stdev(List<Double> list) {
        return Math.sqrt(list.stream().collect(Collectors.summingDouble(x -> Math.pow(x - mean(list), 2))) / list.size());
    }
}
