
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Algorithm {

    private String desiredText = new String();
    private String outputText = new String();
    private List<List<String>> allText = new ArrayList<>();
    private static final int FIRST_GEN_SIZE = 1000;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,\" ";
    private static final Random random = new Random();

    public Algorithm(String text) {
        desiredText = text;
    }

    public void run() {
        allText.add(generateFirstGen());
        printList(allText.get(0));
        // while (!outputText.equals(desiredText)) {

        // }
    }

    private List<String> generateFirstGen() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < FIRST_GEN_SIZE; i++) {
            result.add(getRandomString(desiredText.length()));
        }
        return result;
    }

    private String getRandomString(int size) {
        String result = new String();
        for (int i = 0; i < size; i++) {
            result += CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        }
        return result;
    }

    private <E> void printList(List<E> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    private int getFitness(String text) {
        int fitness = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == desiredText.charAt(i)) fitness++;
        }
        return fitness;
    }
}
