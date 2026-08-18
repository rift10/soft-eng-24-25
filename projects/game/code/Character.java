package code;

import code.Character;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Character implements KeyListener {

    private int x = Main.ENVIRONMENT_WIDTH / 2;
    private int y = Main.ENVIRONMENT_HEIGHT / 2;

    private int dx, dy;

    private int currentKeyChar;
    private int currentKeyCode;
    private int previousKeyCode;

    private final int kMoveAmount = 3;
    private final int kWidth = 100;
    private final int kHeight = 100;
    private int score = 0;

    private static final ImageIcon ii = new ImageIcon("projects/game/images/GreenSquare.jpg");
    private final Image image = ii.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);

    private static final Character instance = null;

    public Character() {
        Timer timer = new Timer(17, (ActionEvent e) -> { periodic(); });
        timer.start();
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

        // use WASD to move
        if (currentKeyCode == KeyEvent.VK_A) dx = -kMoveAmount;
        if (currentKeyCode == KeyEvent.VK_D) dx =  kMoveAmount;
        if (currentKeyCode == KeyEvent.VK_W) dy = -kMoveAmount;
        if (currentKeyCode == KeyEvent.VK_S) dy =  kMoveAmount;

        System.out.println("key pressed");
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        previousKeyCode = e.getKeyCode();

        // reset dx and dy when a key is released
        if (previousKeyCode == KeyEvent.VK_A || previousKeyCode == KeyEvent.VK_D) dx = 0;
        if (previousKeyCode == KeyEvent.VK_W || previousKeyCode == KeyEvent.VK_S) dy = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    public void move() {
        // if the sprite is out of bounds, set dx or dy to zero
        if (x <= -10 && dx <= 0) dx = 0;
        if (x >= Environment.getInstance().getWidth() && dx >= 0) dx = 0;

        if (y >= Environment.getInstance().getHeight() && dy >= 0) dy = 0;
        if (y <= -10 && dy <= 0) dy = 0;

        // update coordinates with input from player
        x += dx;
        y += dy;
    }

    public boolean isTouchingFood() {
        // didnt have time to debug but this doesnt work
        for (int i = 0; i < kWidth; i++) {
            // if (getHitboxWidth().contains(Main.food.getHitboxWidth().get(i))) return true;
            if (getHitboxWidth().contains(Food.getInstance().getHitboxWidth().get(i))) {
                System.out.println("touching width: " + Food.getInstance().getHitboxWidth().get(i));
                return true;
            }
        }

        for (int i = 0; i < kHeight; i++) {
            // if (getHitboxHeight().contains(Main.food.getHitboxHeight().get(i))) return true;
            if (getHitboxHeight().contains(Food.getInstance().getHitboxHeight().get(i))) {
                System.out.println("touching height: " + Food.getInstance().getHitboxHeight().get(i));
                return true;
            }
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
        return kWidth;
    }

    public int getHeight() {
        return kHeight;
    }

    public int getScore() {
        return score;
    }

    public Image getImage() {
        return image;
    }

    /**
     * Creates an arraylist of all the x coordinates
     * encompassed by the character to find the hitbox
     */
    public ArrayList<Integer> getHitboxWidth() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < kWidth; i++) {
            result.add(x + i);
        }
        return result;
    }

    public ArrayList<Integer> getHitboxHeight() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < kHeight; i++) {
            result.add(y + i);
        }
        return result;
    }

}
