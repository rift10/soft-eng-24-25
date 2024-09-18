import java.util.concurrent.TimeUnit;

public class Main {

    public static Character character = Character.getInstance();
    public static Environment environment = Environment.getInstance();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            character.periodic();
            environment.periodic();
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }
}
