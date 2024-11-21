
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import randomvariables.LogNormalRandomVariable;
import randomvariables.NormalRandomVariable;
import randomvariables.UniformRandomVariable;
import work.ParallelWork;
import work.SequentialWork;
import work.Work;


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

    public static void uniformRandom(int min, int max) {
        var uniformRandom = new UniformRandomVariable(min, max);
        var uniformList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) uniformList.add(uniformRandom.next());
        System.out.println("uniform actual mean: " + uniformList.stream().collect(Collectors.averagingDouble(x -> x)));
        System.out.println("uniform expected mean: " + (Util.mean(min, max)));
        System.out.println();
    }

    public static void normalRandom(int mean, int stdev) {
        var normalRandom = new NormalRandomVariable(mean, stdev);
        var normalList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) normalList.add(normalRandom.next());
        var normalActualMean = normalList.stream().collect(Collectors.averagingDouble(x -> x));
        System.out.println("normal actual mean: " + normalActualMean);
        System.out.println("normal expected mean: " + mean);
        System.out.println("normal actual standard deviation: " + Util.stdev(normalList, normalActualMean, times));
        System.out.println("normal expected standard deviation: " + stdev);
        System.out.println();
    }

    public static void logNormalRandom(int mean, int stdev) {
        var logRandom = new LogNormalRandomVariable(mean, stdev);
        var logList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) logList.add(logRandom.next());
        var logActualMean = logList.stream().collect(Collectors.averagingDouble(x -> Math.log(x)));
        System.out.println("log actual mean: " + logActualMean);
        System.out.println("log expected mean: " + mean);
        System.out.println("log actual standard deviation: " + Util.logNormalStddev(logList, logActualMean, times));
        System.out.println("log expected standard deviation: " + stdev);
        System.out.println();
    }

    public static void uniformInterval(int lowerBound, int upperBound) {
        var uniformInterval = new Interval(lowerBound, upperBound);
        var uniformRandomInterval = Interval.uniformRandomWithInterval(uniformInterval);
        var uniformRandomIntervalList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) uniformRandomIntervalList.add(uniformRandomInterval.next());
        uniformRandomIntervalList.sort(Comparator.naturalOrder());
        var uniformFivePercent = (int) (times * 0.05);
        var uniformNinetyIntervalStart = uniformRandomIntervalList.get(uniformFivePercent);
        var uniformNinetyIntervalEnd = uniformRandomIntervalList.get(times - uniformFivePercent);
        System.out.println("uniform actual 90% interval lower bound: " + uniformNinetyIntervalStart + ", upper bound: " + uniformNinetyIntervalEnd);
        System.out.println("uniform expected 90% interval " + uniformInterval.toString());
        System.out.println();
    }

    public static void normalInterval(int lowerBound, int upperBound) {
        var normalInterval = new Interval(lowerBound, upperBound);
        var normalRandomInterval = Interval.normalRandomWithInterval(normalInterval);
        var normalRandomIntervalList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) normalRandomIntervalList.add(normalRandomInterval.next());
        normalRandomIntervalList.sort(Comparator.naturalOrder());
        var normalFivePercent = (int) (times * 0.05);
        var normalNinetyIntervalStart = normalRandomIntervalList.get(normalFivePercent);
        var normalNinetyIntervalEnd = normalRandomIntervalList.get(times - normalFivePercent);
        System.out.println("normal actual 90% interval lower bound: " + normalNinetyIntervalStart + ", upper bound: " + normalNinetyIntervalEnd);
        System.out.println("normal expected 90% interval " + normalInterval.toString());
        System.out.println();
    }

    public static void logInterval(int lowerBound, int upperBound) {
        var logInterval = new Interval(lowerBound, upperBound);
        var logRandomInterval = Interval.normalRandomWithInterval(logInterval);
        var logRandomIntervalList = new ArrayList<Double>();
        for (int i = 0; i < times; i++) logRandomIntervalList.add(logRandomInterval.next());
        logRandomIntervalList.sort(Comparator.naturalOrder());
        var logFivePercent = (int) (times * 0.05);
        var logNinetyIntervalStart = logRandomIntervalList.get(logFivePercent);
        var logNinetyIntervalEnd = logRandomIntervalList.get(times - logFivePercent);
        System.out.println("log actual 90% interval lower bound: " + logNinetyIntervalStart + ", upper bound: " + logNinetyIntervalEnd);
        System.out.println("log expected 90% interval " + logInterval.toString());
        System.out.println();
    }

    private static boolean willFinishInTime(int estimate, double uncertainty, int daysToDo) {
        var logNormal = new LogNormalRandomVariable(estimate, uncertainty);
        return Math.log(logNormal.next()) < daysToDo;
    }

    private static double rollDice(int sides) {
        return new UniformRandomVariable(0, sides).next();
    }

    private static double getChanceWillFinish(int estimatedDays, int uncertainty, int totalDays, int delayThresholdPercent) {
        for (int i = 0; i < uncertainty; i++)
            if (rollDice(uncertainty) < uncertainty * delayThresholdPercent * 0.01) estimatedDays += 1;

        double timesWillFinish = 0;
        for (int i = 0; i < times; i++) timesWillFinish += willFinishInTime(estimatedDays, uncertainty, totalDays) ? 1 : 0;
        double probability = (timesWillFinish / times) * 100;
        return probability;
    }

    public static void testBooleanMethod() {
        var list = new ArrayList<Double>();
        int estimatedDays = 12 + 12 + 5 + 10;
        // int estimatedDays = 14 + 21 + 7 + 14;
        int uncertainty = 1;
        int totalDays = 42;
        int delayThresholdPercent = 100;
        for (int i = 0; i < 10; i++) list.add(getChanceWillFinish(estimatedDays, uncertainty, totalDays, delayThresholdPercent));
        list.sort(Comparator.naturalOrder());
        for (Double i : list) System.out.println(i + "% chance to finish");
    }

    public static void main(String[] args) {
        // testing
        // uniformRandom(uniformMin, uniformMax);
        // normalRandom(normalMean, normalStdev);
        // logNormalRandom(logMean, logStdev);
        // uniformInterval(intervalLowerBound, intervalUpperBound);
        // normalInterval(intervalLowerBound, intervalUpperBound);
        // logInterval(intervalLowerBound, intervalUpperBound);

        var sequentialWork = new SequentialWork(new Work(10, 0), new Work(9, 0));
        var parallelWork = new ParallelWork(new Work(11, 0), sequentialWork);
        
        // var parallelWork = new ParallelWork(new Work(2, 0), new Work(7, 0));
        // var sequentialWork = new SequentialWork(new Work(10, 0), new Work(5, 0), parallelWork);

        var result = new ArrayList<Double>();
        for (int i = 0; i < 10; i++) result.add(parallelWork.generateEndTime());
        result.sort(Comparator.naturalOrder());
        for (double x : result) System.out.println(x);
        System.out.println("actual stdev: " + Util.stdev(result, Util.mean(result), result.size()));
    }
}
