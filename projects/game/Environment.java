
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Environment {

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private Character character;
    private static Environment instance = null;

    public Environment() {

        character = Main.character;
        panel.addKeyListener(character);

        label.setBounds(20, 50, 300, 50);
        panel.add(label);
        panel.setSize(400, 400);
        panel.setLayout(null);
        panel.setVisible(true);
    }

    public static Environment getInstance() {
        if (instance == null) return new Environment();
        return instance;
    }

    public void periodic() {
        label.setText("key pressed: " + String.valueOf(character.getCurrentKeyChar()) + ", coordinate: " + character.getX() + ", " + character.getY());
    }

}
