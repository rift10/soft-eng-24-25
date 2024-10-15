package code;

import code.Character;
import javax.swing.JFrame;

public class Main {
    public static final Environment environment = new Environment();
    public static final Character character = new Character();
    public static final Food food = new Food();

    public static final int ENVIRONMENT_WIDTH = 1440;
    public static final int ENVIRONMENT_HEIGHT = 800;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(ENVIRONMENT_WIDTH, ENVIRONMENT_HEIGHT);
        frame.add(environment);
        frame.setVisible(true);
    }
}
