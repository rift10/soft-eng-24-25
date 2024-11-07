
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;


public class MonteCarlo {
    
    public static void main(String[] args) {
        int times = 100000;
        System.out.println();

        int min = 0;
        int max = 100;
        var uniformRandom = new UniformRandomVariable(min, max);
        var uniformList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) uniformList.add(uniformRandom.next());
        System.out.println("uniform actual mean: " + uniformList.stream().collect(Collectors.averagingDouble(x -> x)));
        System.out.println("uniform expected mean: " + ((min + max) / 2));
        System.out.println();

        int normalMean = 7;
        int normalStdev = 2;
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

        int logMean = 5;
        int logStdev = 1;
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

        int lowerBound = 5;
        int upperBound = 95;
        var uniformInterval = new Interval(lowerBound, upperBound);
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

        var normalInterval = new Interval(lowerBound, upperBound);
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

        var logInterval = new Interval(lowerBound, upperBound);
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
