package code;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Environment extends JFrame implements Runnable {

    private JLabel label = new JLabel();
    private Character character;
    private static Environment instance = null;

    // for thread debugging
    private int runCounter = 0;

    public Environment() {

        character = Character.getInstance();
        addKeyListener(character);

        label.setBounds(20, 50, 300, 50);
        add(label);
        setSize(400, 400);
        setLayout(null);
        setVisible(true);
    }

    public static Environment getInstance() {
        if (instance == null) return new Environment();
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            label.setText("key pressed: " + String.valueOf(character.getCurrentKeyChar()) + ", coordinate: " + character.getX() + ", " + character.getY());
            paintComponents(getGraphics());
            runCounter++;
        }
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
