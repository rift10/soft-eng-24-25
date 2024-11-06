
import java.util.ArrayList;
import java.util.stream.Collectors;


public class MonteCarlo {
    
    public static void main(String[] args) {
        int times = 100000;
        System.out.println();

        int min = 0;
        int max = 100;
        UniformRandomVariable uniformRandomVariable = new UniformRandomVariable(min, max);
        ArrayList<Double> uniformList = new ArrayList<>();
        for (int i = 0; i < times; i++) uniformList.add(uniformRandomVariable.next());
        System.out.println("uniform mean: " + uniformList.stream().collect(Collectors.averagingDouble(x -> x)));
        System.out.println("uniform expected mean: " + ((min + max) / 2));
        System.out.println("uniform standard deviation: " + (Math.pow((max - min), 2) / 12));
        System.out.println();

        int normalMean = 7;
        int normalStdev = 2;
        NormalRandomVariable normalRandomVariable = new NormalRandomVariable(normalMean, normalStdev);
        ArrayList<Double> normalList = new ArrayList<>();
        for (int i = 0; i < times; i++) normalList.add(normalRandomVariable.next());
        var normalActualMean = normalList.stream().collect(Collectors.averagingDouble(x -> x));
        var normalActualStdev = Math.sqrt(normalList.stream().collect(Collectors.summingDouble(x -> Math.pow(x - normalActualMean, 2))) / times);
        System.out.println("normal mean: " + normalActualMean);
        System.out.println("normal expected mean: " + normalMean);
        System.out.println("normal standard deviation: " + normalActualStdev);
        System.out.println("normal expected standard deviation: " + normalStdev);
        System.out.println();

        int logMean = 5;
        int logStdev = 1;
        LogNormalRandomVariable logNormalRandomVariable = new LogNormalRandomVariable(logMean, logStdev);
        ArrayList<Double> logList = new ArrayList<>();
        for (int i = 0; i < times; i++) logList.add(logNormalRandomVariable.next());
        var logActualMean = logList.stream().collect(Collectors.averagingDouble(x -> Math.log(x)));
        var logActualStdev = Math.sqrt(logList.stream().collect(Collectors.summingDouble(x -> Math.pow(Math.log(x) - logActualMean, 2))) / times);
        System.out.println("log mean: " + logActualMean);
        System.out.println("log expected mean: " + logMean);
        System.out.println("log standard deviation: " + logActualStdev);
        System.out.println("log expected standard deviation: " + logStdev);
    }
}
