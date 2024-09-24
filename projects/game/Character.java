
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

public class Character implements KeyListener, Runnable {

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
    // private int width, height;

    // for thread debugging
    private int runCounter = 0;

    private int currentKeyChar;
    private int currentKeyCode;
    private int previousKeyCode;

    private Image image;

    private static Direction direction = Direction.NONE;

    private static Character instance = null;

    public Character() {
        ImageIcon ii = new ImageIcon("projects/game/Image.png");
        image = ii.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);

        // width = image.getWidth(null);
        // height = image.getHeight(null);
    }

    public static Character getInstance() {
        if (instance == null) return new Character();
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            move();
            runCounter++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
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
    
    public void move() {
        x += dx;
        y += dy;
    }

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

    public Image getImage() {
        return image;
    }

    public int getRunCounter() {
        return runCounter;
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