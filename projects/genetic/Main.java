
import java.util.List;
import java.util.Random;

public class Main {
    public static Random random = new Random();
    
    public static SalesGUI salesGUI = new SalesGUI();
    public static SalesAlgorithm salesAlgorithm;

    public static ImageGUI imageGUI = new ImageGUI();
    public static ImageAlgorithm imageAlgorithm;

    public static void main(String[] args) {
        // new TextAlgorithm("To be or not to be, that is the question.").run();

        salesAlgorithm = new SalesAlgorithm(
            salesGUI,
            List.of(
                new SalesAlgorithm.City("A", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("B", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("C", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("D", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("E", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("F", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("G", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("H", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("I", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("J", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50)),
                new SalesAlgorithm.City("K", (int) (random.nextDouble() * 500 + 50), (int) (random.nextDouble() * 500 + 50))
            ));

        // salesGUI.start();
        // salesAlgorithm.start();

        imageAlgorithm = new ImageAlgorithm(imageGUI, "/workspaces/rift10/projects/genetic/images/MonaLisa.jpg");
        // imageAlgorithm = new ImageAlgorithm(imageGUI, "/workspaces/rift10/projects/genetic/images/Apple.png");
        imageGUI.start();
        imageAlgorithm.start();
    }
}