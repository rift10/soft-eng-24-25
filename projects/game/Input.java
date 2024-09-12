import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Input extends JFrame implements KeyListener, MouseListener {

    public enum Direction {
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    private int mCurrentKeyCode;
    private char mCurrentKeyChar;
    private Direction mCurrentDirection = Direction.NONE;

    private JLabel mLabel = new JLabel();
    private JTextArea mTextArea = new JTextArea();

    public Input() {
        mTextArea.addKeyListener(this);
        mLabel.setBounds(20, 50, 100, 50);
        mTextArea.setBounds(20, 80, 300, 300);
        add(mLabel);
        add(mTextArea);
        setSize(400, 400);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        mLabel.setText(String.valueOf(e.getKeyChar()));
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

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

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
