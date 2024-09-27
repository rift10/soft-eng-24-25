package code;

import code.Character;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Environment extends JFrame {

    private final JLabel label = new JLabel();
    private final Character character;
    private final Enemy enemy;
    private final static Environment instance = null;

    private final Timer timer = new Timer(3, (ActionEvent e) -> { periodic(); });

    public Environment() {

        character = Character.getInstance();
        enemy = Enemy.getInstance();
        addKeyListener(character);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label.setBounds(20, 50, 300, 50);
        add(label);
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
        // label.setText(enemy.getDX() + ", " + enemy.getDY());
        // TODO: fix flickering issue
        repaint();
    }

    // @Override
    // public void paintAll(Graphics g) {
    //     super.paintComponents(g);
    //     draw(g);
    // }

    @Override
    public void repaint() {
        super.repaint();
        draw(getGraphics());
        // super.paintComponents(getGraphics());
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(character.getImage(), character.getX(), character.getY(), this);
        g2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
        g2d.fillRect(100, 100, 100, 100);
        super.update(g); 
    }

    public void startTimer() {
        timer.start();
    }

}
