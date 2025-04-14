
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SalesAlgorithm {

    private static final Random random = new Random();
    private static final int GEN_SIZE = 1000;
    private static final int NUM_GENS = 100;
    private static final double MUTATE_RATE = 0.02;

    private static double currentFitness = Integer.MAX_VALUE;
    private static double bestFitness = Integer.MAX_VALUE;
    private final List<List<List<City>>> allGens = new ArrayList<>();
    private final List<City> cities;
    private List<City> outputPath = new ArrayList<>();

    public SalesAlgorithm(List<City> cities) {
        this.cities = cities;
    }

    public void run() {
        allGens.add(generateFirstGen());
        // allGens.get(0).forEach((path) -> printCities(path));
        currentFitness = getBestFitness(0);
        bestFitness = getBestFitness(0);
        System.out.println("Gen 0 Fitness: " + getBestFitness(0));
        System.out.print("Gen 0 Result: ");
        printCities(getBestPath(0));
        int index = 1;
        boolean improving = false;
        while (improving ? index <= NUM_GENS : true) {
            allGens.add(generateNextGen(allGens.get(index-1)));
            currentFitness = getBestFitness(index);
            outputPath = getBestPath(index);
            // allGens.get(index).forEach((path) -> printCities(path));
            bestFitness = Math.min(bestFitness, currentFitness);
            improving = bestFitness == currentFitness;
            System.out.println("Gen " + index + " Fitness: " + currentFitness);
            System.out.print("Gen " + index + " Result: ");
            printCities(outputPath);
            index++;
        }
    }

    /** Generates the next generation from the previous one */
    private List<List<City>> generateNextGen(List<List<City>> previousGen) {
        List<List<City>> result = new ArrayList<>();
        for (int i = 0; i < previousGen.size()/2; i++) {
            List<City> parentOne = previousGen.get(i);
            List<City> parentTwo = previousGen.get(i + previousGen.size()/2);
            for (int j = 0; j < 2; j++) {
                List<City> child = new ArrayList<>();
                child.add(getDistance(parentOne.get(0), parentOne.get(1)) < getDistance(parentTwo.get(0), parentTwo.get(1))
                                ? parentOne.get(0) : parentTwo.get(0));
                for (int k = 1; k < parentOne.size(); k++) {
                    if (random.nextDouble() < MUTATE_RATE) {
                        child.add(randomCity(child));
                    } else if (getDistance(parentOne.get(k - 1), parentOne.get(k)) < getDistance(parentTwo.get(k), parentTwo.get(k - 1))
                            && !child.contains(parentOne.get(k))) {
                        child.add(parentOne.get(k));
                    } else if (!child.contains(parentTwo.get(k))) {
                        child.add(parentTwo.get(k));
                    } else {
                        child.add(randomCity(child));
                    }
                }
                result.add(child);
            }
        }
        return result;
    }

    /** Returns the fitness of the best path in a specified generation */
    private double getBestFitness(int gen) {
        return fitness(getBestPath(gen));
    }

    /** Returns the path with the highest fitness in a specified generation */
    private List<City> getBestPath(int gen) {
        double fitness = Integer.MAX_VALUE;
        List<City> result = new ArrayList<>();
        for (int i = 0; i < allGens.get(gen).size(); i++) {
            if (fitness(allGens.get(gen).get(i)) < fitness) {
                fitness = fitness(allGens.get(gen).get(i));
                result = allGens.get(gen).get(i);
            }
        }
        return result;
    }

    /** Returns the fitness of the specified path */
    private double fitness(List<City> path) {
        double fitness = 0;
        for (int i = 0; i < path.size() -1; i++) {
            fitness += getDistance(path.get(i), path.get(i + 1));
        }
        return fitness;
    }

    /** Generates a list of lists of random Cities to fill the first generation */
    private List<List<City>> generateFirstGen() {
        List<List<City>> result = new ArrayList<>();
        for (int i = 0; i < GEN_SIZE; i++) {
            List<City> path = new ArrayList<>();
            for (int j = 0; j < cities.size(); j++) {
                path.add(randomCity(path));
            }
            result.add(path);
        }
        return result;
    }

    private double getDistance(City a, City b) {
        return Math.hypot((a.x() - b.x()), (a.y() - b.y()));
    }

    private City randomCity(List<City> otherCities) {
        City result = randomCity();
        while (otherCities.contains(result)) {
            result = randomCity();
        }
        return result;
    }

    private City randomCity() {
        return cities.get((int) Math.round(random.nextDouble() * (cities.size() - 1)));
    }

    public void printCities(List<City> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).name() + ((i == list.size() - 1) ? "" : ", "));
        }
        System.out.println();
    }

    public record City(String name, int x, int y) {}
}
