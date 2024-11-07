
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;


public class MonteCarlo {

    // constants
    public static int times = 100000;
    public static int uniformMin = 0;
    public static int uniformMax = 100;
    public static int normalMean = 7;
    public static int normalStdev = 2;
    public static int logMean = 5;
    public static int logStdev = 1;
    public static int intervalLowerBound = 5;
    public static int intervalUpperBound = 95;
    
    public static void main(String[] args) {
        var uniformRandom = new UniformRandomVariable(uniformMin, uniformMax);
        var uniformList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) uniformList.add(uniformRandom.next());
        System.out.println("uniform actual mean: " + uniformList.stream().collect(Collectors.averagingDouble(x -> x)));
        System.out.println("uniform expected mean: " + ((uniformMin + uniformMax) / 2));
        System.out.println();

        var normalRandom = new NormalRandomVariable(normalMean, normalStdev);
        var normalList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) normalList.add(normalRandom.next());
        var normalActualMean = normalList.stream().collect(Collectors.averagingDouble(x -> x));
        var normalActualStdev = Math.sqrt(normalList.stream().collect(Collectors.summingDouble(x -> Math.pow(x - normalActualMean, 2))) / times);
        System.out.println("normal actual mean: " + normalActualMean);
        System.out.println("normal expected mean: " + normalMean);
        System.out.println("normal actual standard deviation: " + normalActualStdev);
        System.out.println("normal expected standard deviation: " + normalStdev);
        System.out.println();

        var logRandom = new LogNormalRandomVariable(logMean, logStdev);
        var logList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) logList.add(logRandom.next());
        var logActualMean = logList.stream().collect(Collectors.averagingDouble(x -> Math.log(x)));
        var logActualStdev = Math.sqrt(logList.stream().collect(Collectors.summingDouble(x -> Math.pow(Math.log(x) - logActualMean, 2))) / times);
        System.out.println("log actual mean: " + logActualMean);
        System.out.println("log expected mean: " + logMean);
        System.out.println("log actual standard deviation: " + logActualStdev);
        System.out.println("log expected standard deviation: " + logStdev);
        System.out.println();

        var uniformInterval = new Interval(intervalLowerBound, intervalUpperBound);
        var uniformRandomInterval = Interval.uniformRandomWithInterval(uniformInterval);
        var uniformRandomIntervalList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) uniformRandomIntervalList.add(uniformRandomInterval.next());
        uniformRandomIntervalList.sort(Comparator.comparingDouble(x -> x));
        var uniformFivePercent = (int) (times * 0.05);
        var uniformNinetyIntervalStart = uniformRandomIntervalList.get(uniformFivePercent);
        var uniformNinetyIntervalEnd = uniformRandomIntervalList.get(times - uniformFivePercent);
        System.out.println("uniform actual 90% interval lower bound: " + uniformNinetyIntervalStart + ", upper bound: " + uniformNinetyIntervalEnd);
        System.out.println("uniform expected 90% interval " + uniformInterval.toString());
        System.out.println();

        var normalInterval = new Interval(intervalLowerBound, intervalUpperBound);
        var normalRandomInterval = Interval.normalRandomWithInterval(normalInterval);
        var normalRandomIntervalList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) normalRandomIntervalList.add(normalRandomInterval.next());
        normalRandomIntervalList.sort(Comparator.comparingDouble(x -> x));
        var normalFivePercent = (int) (times * 0.05);
        var normalNinetyIntervalStart = normalRandomIntervalList.get(normalFivePercent);
        var normalNinetyIntervalEnd = normalRandomIntervalList.get(times - normalFivePercent);
        System.out.println("normal actual 90% interval lower bound: " + normalNinetyIntervalStart + ", upper bound: " + normalNinetyIntervalEnd);
        System.out.println("normal expected 90% interval " + normalInterval.toString());
        System.out.println();

        var logInterval = new Interval(intervalLowerBound, intervalUpperBound);
        var logRandomInterval = Interval.normalRandomWithInterval(logInterval);
        var logRandomIntervalList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) logRandomIntervalList.add(logRandomInterval.next());
        logRandomIntervalList.sort(Comparator.comparingDouble(x -> x));
        var logFivePercent = (int) (times * 0.05);
        var logNinetyIntervalStart = logRandomIntervalList.get(logFivePercent);
        var logNinetyIntervalEnd = logRandomIntervalList.get(times - logFivePercent);
        System.out.println("log actual 90% interval lower bound: " + logNinetyIntervalStart + ", upper bound: " + logNinetyIntervalEnd);
        System.out.println("log expected 90% interval " + normalInterval.toString());
    }
}
