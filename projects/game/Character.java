import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Character implements KeyListener {

    public enum Direction {
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    private ImageIcon image;
    private int x = 0;
    private int y = 0;
    private int dx;
    private int dy;

    private int currentKeyCode;
    private char currentKeyChar;
    private static Direction direction = Direction.NONE;

    private JFrame frame = new JFrame();
    private JLabel label = new JLabel();
    private JTextArea textArea = new JTextArea();

    public Character() {
        image = new ImageIcon("..."); // TODO: get an image
        textArea.addKeyListener(this);
        label.setBounds(20, 50, 100, 50);
        textArea.setBounds(20, 80, 300, 300);
        frame.add(label);
        frame.add(textArea);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void periodic() {

        switch (direction) {
            case NONE:
                dx = 0;
                dy = 0;
                break;

            case LEFT:
                dx = -10;

                break;

            case UP:
                dy = 10;
                break;

            case RIGHT:
                dx = 10;
                break;

            case DOWN:
                dy = -10;
                break;
                
        }

        move();
    }

    // TODO: move keyboard logic here?

    @Override
    public void keyPressed(KeyEvent e) {
        label.setText(String.valueOf(e.getKeyChar()));
        currentKeyCode = e.getKeyCode();
        currentKeyChar = e.getKeyChar();

        if (currentKeyCode == KeyEvent.VK_A) {
            direction = Direction.LEFT;
        } else if (currentKeyCode == KeyEvent.VK_D) {
            direction = Direction.RIGHT;
        } else if (currentKeyCode == KeyEvent.VK_W) {
            direction = Direction.UP;
        } else if (currentKeyCode == KeyEvent.VK_S) {
            direction = Direction.DOWN;
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public Direction getDirection() {
        return direction;
    }

}
