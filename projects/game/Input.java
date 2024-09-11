import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input extends KeyAdapter {

    private enum Direction {
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    private int mCurrentKeyCode;
    private char mCurrentKeyChar;
    private Direction mCurrentDirection = Direction.NONE;

    public Input() {}

    @Override
    public void keyPressed(KeyEvent e) {
        mCurrentKeyCode = e.getKeyCode();
        mCurrentKeyChar = e.getKeyChar();

        if (mCurrentKeyCode == KeyEvent.VK_LEFT) {
            mCurrentDirection = Direction.LEFT;
        } else if (mCurrentKeyCode == KeyEvent.VK_RIGHT) {
            mCurrentDirection = Direction.RIGHT;
        } else if (mCurrentKeyCode == KeyEvent.VK_UP) {
            mCurrentDirection = Direction.UP;
        } else if (mCurrentKeyCode == KeyEvent.VK_DOWN) {
            mCurrentDirection = Direction.DOWN;
        } 
    }

    public int getCurrentKeyCode() {
        return mCurrentKeyCode;
    }

    public char getCurrentKey() {
        return mCurrentKeyChar;
    }

    public Direction getDirection() {
        return mCurrentDirection;
    }

}
