
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TextAlgorithm {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789., ";
    private static final Random random = new Random();
    private static final int GEN_SIZE = 1000;
    private static final double MUTATE_RATE = 0.02;

    private static int bestFitness = 0;
    private final List<List<String>> allGens = new ArrayList<>();
    private final String desiredText;
    private String outputText = new String();

    public TextAlgorithm(String text) {
        desiredText = text;
    }

    public void run() {
        allGens.add(generateFirstGen());
        // allGens.get(0).forEach((s) -> System.out.println(s));
        for (String s : allGens.get(0)) {
            if (fitness(s) > bestFitness) bestFitness = fitness(s);
        }
        System.out.println("Best fitness: " + bestFitness);
        System.out.println("Gen 0 Result: " + getBestFitness(0));
        int index = 1;
        while (!outputText.equals(desiredText)) {
            allGens.add(generateNextGen(allGens.get(index-1)));
            outputText = getBestFitness(index);
            // allGens.get(index).forEach((s) -> System.out.println(s));
            System.out.println("Gen " + index + " Result: " + outputText);
            index++;
        }
    }

    /** Generates the next generation from the previous one */
    private List<String> generateNextGen(List<String> previousGen) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < previousGen.size()/2; i++) {
            String parentOne = previousGen.get(i);
            String parentTwo = previousGen.get(i + previousGen.size()/2);
            for (int j = 0; j < 2; j++) {
                String child = new String();
                for (int k = 0; k < parentOne.length(); k++) {
                    if (random.nextDouble() < MUTATE_RATE) {
                        child += randomChar();
                    } else if (parentOne.charAt(k) == desiredText.charAt(k)) {
                        child += parentOne.charAt(k);
                    } else if (parentTwo.charAt(k) == desiredText.charAt(k)) {
                        child += parentTwo.charAt(k);
                    } else {
                        child += random.nextDouble() < 0.5 ? parentOne.charAt(k) : parentTwo.charAt(k); 
                    }
                }
                result.add(child);
            }
        }
        return result;
    }

    /** Returns the String with the highest fitness in a specified generation */
    private String getBestFitness(int gen) {
        int fitness = 0;
        String result = new String();
        for (int i = 0; i < allGens.get(gen).size(); i++) {
            if (fitness(allGens.get(gen).get(i)) > fitness) {
                fitness = fitness(allGens.get(gen).get(i));
                result = allGens.get(gen).get(i);
            }
        }
        return result;
    }

    private int fitness(String text) {
        int fitness = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == desiredText.charAt(i)) fitness++;
        }
        return fitness;
    }

    /** Generates a list of random Strings to fill the first generation */
    private List<String> generateFirstGen() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < GEN_SIZE; i++) {
            result.add(getRandomString(desiredText.length()));
        }
        return result;
    }

    private String getRandomString(int size) {
        String result = new String();
        for (int i = 0; i < size; i++) {
            result += randomChar();
        }
        return result;
    }

    private char randomChar() {
        return CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
    }

}
