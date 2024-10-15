package code;

import code.Character;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Environment extends JPanel {

    private final JLabel label = new JLabel();
    private final Character character;
    private final Food enemy;
    private final static Environment instance = null;

    private int xTemp = 0;
    private int yTemp = 0;

    public Environment() {

        Timer timer = new Timer(17, (ActionEvent e) -> { periodic(); });
        timer.start();

        character = Character.getInstance();
        enemy = Food.getInstance();
        addKeyListener(character);
        setFocusable(true);
        requestFocusInWindow(false);

        label.setBounds(20, 50, 300, 50);
        add(label);
        // setSize(kWidth, kHeight);
        // setLayout(null);

        label.setText("initialized environment");
        
    }

    public static Environment getInstance() {
        if (instance == null) return new Environment();
        return instance;
    }

    public void periodic() {
        if (character.getCurrentKeyCode() != 0) System.out.println("current key code: " + character.getCurrentKeyCode());
        // label.setText("is player touching food: " + character.isTouchingFood());
        // System.out.println("is player touching food: " + character.isTouchingFood());
        // System.out.println("score: " + character.getScore());
        // label.setText(food.getDX() + ", " + food.getDY());

        xTemp += 0.1;
        yTemp += 0.1;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        label.setText("painting component");
        super.paintComponent(g);
        // drawBackground(g);
        draw(g);
    }

    public void drawBackground(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Main.ENVIRONMENT_WIDTH, Main.ENVIRONMENT_HEIGHT);
    }

    public void draw(Graphics g) {
        // label.setText("drawing sprites");
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(character.getImage(), character.getX(), character.getY(), this);
        g2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
    }

}
