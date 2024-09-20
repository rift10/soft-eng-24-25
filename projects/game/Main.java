
public class Main {

    public static Character character = Character.getInstance();
    public static Environment environment = Environment.getInstance();

    public static void main(String[] args) throws InterruptedException {

        character.start();

        while (true) {
            character.run();
            environment.periodic();
            System.out.println("character runs: " + character.getRunCounter() + ", environment runs: " + environment.getRunCounter());
        }
    }
}
