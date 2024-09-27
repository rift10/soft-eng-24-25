package code;

import code.Character;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Environment extends JFrame {

    // private final JLabel label = new JLabel();
    private final Character character;
    private final Enemy enemy;
    private final static Environment instance = null;

    private final Timer timer = new Timer(2, (ActionEvent e) -> { periodic(); });

    // for thread debugging
    private int runCounter = 0;

    public Environment() {

        character = Character.getInstance();
        enemy = Enemy.getInstance();
        addKeyListener(character);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        // TODO: fix flickering issue
        repaint();
        runCounter++;
    }

    // @Override
    // public void paintAll(Graphics g) {
    //     super.paintComponents(g);
    //     draw(g);
    // }

    @Override
    public void repaint() {
        draw(getGraphics());
        // super.paintComponents(getGraphics());
        super.repaint();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(character.getImage(), character.getX(), character.getY(), this);
        g2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
    }

    public void startTimer() {
        timer.start();
    }


    /* ------------------ getters --------------------- */

    public int getRunCounter() {
        return runCounter;
    }

}
