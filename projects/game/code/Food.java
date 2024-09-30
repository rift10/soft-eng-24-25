package code;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Food {

    private int x = 0;
    private int y = 0;

    private double dx, dy;

    private int width, height;

    private final int kMaxMove = 10;

    private static final ImageIcon ii = new ImageIcon("projects/game/images/RedSquare.png");
    private final Image image = ii.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
    
    private final Random random = new Random();

    private static final Food instance = null;

    public Food() {
        Timer timer = new Timer(1, (ActionEvent e) -> { periodic(); });
        timer.start();

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public static Food getInstance() {
        if (instance == null) return new Food();
        return instance;
    }

    public void periodic() {
        dx = random.nextBoolean() ? random.nextInt(kMaxMove) : -random.nextInt(kMaxMove);
        dy = random.nextBoolean() ? random.nextInt(kMaxMove) : -random.nextInt(kMaxMove);
        move();
    }
    
    public void move() {
        if (x <= -10)  dx =  Math.abs(dx);
        if (x >= 1330) dx = -Math.abs(dx);

        if (y >= 600)  dy = -Math.abs(dy);
        if (y <= -10)  dy =  Math.abs(dy);

        x += dx;
        y += dy;

        // System.out.println(x + ", " + y);

        // corner: 1338, 645
    }

    /* ------------------ getters --------------------- */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDX() {
        return dx;
    }

    public double getDY() {
        return dy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
