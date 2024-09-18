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

    private int x = 0;
    private int y = 0;

    private int dx, dy;

    private int currentKeyChar;
    private int currentKeyCode;
    private int previousKeyCode;

    private static Direction direction = Direction.NONE;

    private static Character instance = null;

    
    // private JFrame frame = new JFrame();
    // private JLabel label = new JLabel();

    public Character() {
        // todo: get an image

        // frame.addKeyListener(this);
        // label.setBounds(20, 50, 300, 50);
        // frame.add(label);
        // frame.setSize(400, 400);
        // frame.setLayout(null);
        // frame.setVisible(true);
    }

    public static Character getInstance() {
        if (instance == null) return new Character();
        return instance;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void periodic() {

        move();

        // todo: draw the character moving here
    }

    @Override
    public void keyPressed(KeyEvent e) {

        currentKeyChar = e.getKeyChar();
        currentKeyCode = e.getKeyCode();

        if (currentKeyCode == KeyEvent.VK_A) dx = -1;
        if (currentKeyCode == KeyEvent.VK_D) dx =  1;
        if (currentKeyCode == KeyEvent.VK_W) dy =  1;
        if (currentKeyCode == KeyEvent.VK_S) dy = -1;
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        previousKeyCode = e.getKeyCode();

        if (previousKeyCode == KeyEvent.VK_A || previousKeyCode == KeyEvent.VK_D) dx = 0;
        if (previousKeyCode == KeyEvent.VK_W || previousKeyCode == KeyEvent.VK_S) dy = 0;

    }

    @Override
    public void keyTyped(KeyEvent e) {}


    /* ------------------ getters --------------------- */

    public Direction getDirection() {
        return direction;
    }

    public int getCurrentKeyChar() {
        return currentKeyChar;
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

// here so i dont have to type it all out again

// switch (direction) {
//     case NONE:
//         break;

//     case WEST:
//         break;

//     case NORTHWEST:
//         break;

//     case NORTH:
//         break;

//     case NORTHEAST:
//         break;

//     case EAST:
//         break;

//     case SOUTHEAST:
//         break;

//     case SOUTH:
//         break;

//     case SOUTHWEST:
//         break;
        
// }