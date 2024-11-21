
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Util {

    public static double mean(double x, double y) {
        return (x + y) / 2;
    }

    public static double mean(ArrayList<Double> summands) {
        return summands.stream().collect(Collectors.summingDouble(x -> x)) / summands.size();
    }
    
    public static double stdev(ArrayList<Double> list, double mean, int times) {
        return Math.sqrt(list.stream().collect(Collectors.summingDouble(x -> Math.pow(x - mean, 2))) / times);
    }

    public static double logNormalStddev(ArrayList<Double> list, double mean, int times) {
        return Math.sqrt(list.stream().collect(Collectors.summingDouble(x -> Math.pow(Math.log(x) - mean, 2))) / times);
    }
}
