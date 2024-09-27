package code;

import code.Character;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Character implements KeyListener {

    private int x = 0;
    private int y = 0;

    private int dx, dy;

    private int currentKeyChar;
    private int currentKeyCode;
    private int previousKeyCode;

    private final int kMove = 3;

    private static final ImageIcon ii = new ImageIcon("projects/game/GreenSquare.jpg");
    private final Image image = ii.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);

    private static final Character instance = null;

    public Character() {

        Timer timer = new Timer(1, (ActionEvent e) -> { periodic(); });
        timer.start();
    }

    public static Character getInstance() {
        if (instance == null) return new Character();
        return instance;
    }

    public void periodic() {
        move();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        currentKeyChar = e.getKeyChar();
        currentKeyCode = e.getKeyCode();

        if (currentKeyCode == KeyEvent.VK_A) dx = -kMove;
        if (currentKeyCode == KeyEvent.VK_D) dx =  kMove;
        if (currentKeyCode == KeyEvent.VK_W) dy = -kMove;
        if (currentKeyCode == KeyEvent.VK_S) dy =  kMove;
        
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

}
