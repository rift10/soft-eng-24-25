import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SalesGUI extends JFrame {
    
    private final SalesGraph panel = new SalesGraph();

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

        Timer timer = new Timer(17, (ActionEvent e) -> run());
        timer.start();
    }

    public void start() {
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void run() {
        revalidate();
        panel.repaint();
    }

    public void updatePoints(List<SalesAlgorithm.City> cities, int gen) {
        panel.updatePoints(cities, gen);
    }
}