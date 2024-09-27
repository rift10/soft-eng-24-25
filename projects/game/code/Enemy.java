package code;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Enemy {

    private int x = 0;
    private int y = 0;

    private double dx, dy;

    private static final ImageIcon ii = new ImageIcon("projects/game/RedSquare.png");
    private final Image image = ii.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);

    private static final Enemy instance = null;

    public Enemy() {
        Timer timer = new Timer(1, (ActionEvent e) -> { periodic(); });
        timer.start();
    }

    public static Enemy getInstance() {
        if (instance == null) return new Enemy();
        return instance;
    }

    public void periodic() {
        dx = Math.random();
        dy = Math.random();
        move();
    }
    
    public void move() {
        x += dx;
        y += dy;
    }

    /* ------------------ getters --------------------- */

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
