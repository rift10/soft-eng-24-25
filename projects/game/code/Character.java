package code;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Character implements KeyListener {

    private int x = 0;
    private int y = 0;

    private int dx, dy;

    private int currentKeyChar;
    private int currentKeyCode;
    private int previousKeyCode;

    private final int moveAmount = 3;
    private final int width, height;
    private int score = 0;

    private static final ImageIcon ii = new ImageIcon("projects/game/images/GreenSquare.jpg");
    private final Image image = ii.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);

    private static final Character instance = null;

    public Character() {

        Timer timer = new Timer(20, (ActionEvent e) -> { periodic(); });
        timer.start();
        
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public static Character getInstance() {
        if (instance == null) return new Character();
        return instance;
    }

    public void periodic() {
        move();

        if (isTouchingFood()) score++;

    }

    @Override
    public void keyPressed(KeyEvent e) {

        currentKeyChar = e.getKeyChar();
        currentKeyCode = e.getKeyCode();

        if (currentKeyCode == KeyEvent.VK_A) dx = -moveAmount;
        if (currentKeyCode == KeyEvent.VK_D) dx =  moveAmount;
        if (currentKeyCode == KeyEvent.VK_W) dy = -moveAmount;
        if (currentKeyCode == KeyEvent.VK_S) dy =  moveAmount;
        
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
        if (x <= -10 && dx <= 0)  dx = 0;
        if (x >= 1340 && dx >= 0) dx = 0;

        if (y >= 650 && dy >= 0)  dy = 0;
        if (y <= -10 && dy <= 0)  dy = 0;

        x += dx;
        y += dy;

    }

    public boolean isTouchingFood() {
        for (int i = 0; i < width; i++) {
            if (getHitboxWidth().contains(Food.getInstance().getHitboxWidth().get(i))) return true;
        }
        return false;
    }

    /* ------------------ getters --------------------- */

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<Integer> getHitboxWidth() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            result.add(y + i);
        }
        return result;
    }

    public ArrayList<Integer> getHitboxHeight() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            result.add(x + i);
        }
        return result;
    }

}
