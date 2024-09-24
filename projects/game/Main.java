
public class Main {

    public static Character character = Character.getInstance();
    public static Environment environment = Environment.getInstance();

    public static Thread characterThread = new Thread(character);
    public static Thread environmentThread = new Thread(environment);

    public static void main(String[] args) throws InterruptedException {

        environmentThread.start();
        characterThread.start();

        // while (true) {
        //     System.out.println("character runs: " + character.getRunCounter() +
        //                     ", environment runs: " + environment.getRunCounter());
        // }
    }
}
