package code;
public class Main {

    public static Character character = Character.getInstance();
    public static Environment environment = Environment.getInstance();
    public static Enemy enemy = Enemy.getInstance();

    public static void main(String[] args) {
        environment.startTimer();
    }
}
