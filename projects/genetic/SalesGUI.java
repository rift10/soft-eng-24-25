import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SalesGUI extends JFrame {
    
    private final SalesGraph panel = SalesGraph.getInstance();
    private int currentGen = 0;
    private Timer timer;

    public SalesGUI() {
        super("Traveling Salesperson");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println(e);
        }
    }

    public void start() {
        timer = new Timer(1, (ActionEvent e) -> panel.periodic());
        timer.start();

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

}