import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Character implements KeyListener {

    public enum Direction {
        NONE,
        WEST,
        NORTHWEST,
        NORTH,
        NORTHEAST,
        EAST,
        SOUTHEAST,
        SOUTH,
        SOUTHWEST
    }

    private int x = 1;
    private int y = 1;

    private int dx, dy;

    private int currentKeyChar;
    private int currentKeyCode;
    private int previousKeyCode;

    private JFrame frame = new JFrame();
    private JLabel label = new JLabel();

    private static Direction direction = Direction.NONE;

    public Character() {
        // todo: get an image

        frame.addKeyListener(this);
        label.setBounds(20, 50, 300, 50);
        frame.add(label);
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

            case WEST:
                dx = -1;

                break;

            case NORTHWEST:
                break;

            case NORTH:
                dy = 1;
                break;

            case NORTHEAST:
                break;

            case EAST:
                dx = 1;
                break;

            case SOUTHEAST:
                break;

            case SOUTH:
                dy = -1;
                break;

            case SOUTHWEST:
                break;
                
        }

        move();

        label.setText("key pressed: " + String.valueOf(currentKeyChar) + ", coordinate: " + x + ", " + y);

        // todo: draw the character moving here
    }

    @Override
    public void keyPressed(KeyEvent e) {

        currentKeyChar = e.getKeyChar();
        currentKeyCode = e.getKeyCode();

        // TODO: fix this (also refactor to look nicer maybe)

        if (currentKeyCode == KeyEvent.VK_A) {
            direction = Direction.WEST;
        } else if (currentKeyCode == KeyEvent.VK_D) {
            direction = Direction.EAST;
        } else if (currentKeyCode == KeyEvent.VK_W) {
            direction = Direction.NORTH;
        } else if (currentKeyCode == KeyEvent.VK_S) {
            direction = Direction.SOUTH;
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        previousKeyCode = e.getKeyCode();

        // TODO: change this (BANDAID FIX)
        direction = Direction.NONE;
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    /* ------------------ getters --------------------- */

    public Direction getDirection() {
        return direction;
    }

    public int getCurrentKeyCode() {
        return currentKeyCode;
    }

    public int getReleasedKeyCode() {
        return previousKeyCode;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
