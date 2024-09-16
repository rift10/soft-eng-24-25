import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

public class Character implements KeyListener {

    private ImageIcon mImage;
    private int x = 0;
    private int y = 0;
    private int dx;
    private int dy;

    private Input.Direction mDirection = Input.Direction.NONE;

    public Character() {
        mImage = new ImageIcon("..."); // TODO: get an image
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void periodic() {
        mDirection = Input.getDirection();

        switch (mDirection) {
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
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
