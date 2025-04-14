import java.util.List;

public class Main {
    public static void main(String[] args) {
        // new TextAlgorithm("To be or not to be, that is the question.").run();
        new SalesAlgorithm(List.of(
            new SalesAlgorithm.City("A", 100, 30),
            new SalesAlgorithm.City("B", 875, 325),
            new SalesAlgorithm.City("C", 25, 40),
            new SalesAlgorithm.City("D", 230, 10),
            new SalesAlgorithm.City("E", 10, 15),
            new SalesAlgorithm.City("F", 127, 827)
        )).run();
    }
}