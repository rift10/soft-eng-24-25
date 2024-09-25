package code;

import code.Character;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class Environment extends JFrame {

    // private final JLabel label = new JLabel();
    private final Character character;
    private final static Environment instance = null;

    // for thread debugging
    private int runCounter = 0;

    public Environment() {

        character = Character.getInstance();
        addKeyListener(character);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Timer timer = new Timer(17, (ActionEvent e) -> { periodic(); });
        // timer.start();

        // label.setBounds(20, 50, 300, 50);
        // add(label);
        setSize(1440, 800);
        setLayout(null);
        setVisible(true);
        
    }

    public static Environment getInstance() {
        if (instance == null) return new Environment();
        return instance;
    }

    public void periodic() {
        // label.setText("key pressed: " + String.valueOf(character.getCurrentKeyChar()) + ", coordinate: " + character.getX() + ", " + character.getY());
        // repaint();
        paintComponents(getGraphics());
        runCounter++;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        draw(g);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(character.getImage(), character.getX(), character.getY(), this);
    }


    /* ------------------ getters --------------------- */

    public int getRunCounter() {
        return runCounter;
    }

}
