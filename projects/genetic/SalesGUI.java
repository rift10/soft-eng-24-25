import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SalesGUI extends JFrame {
    
    private final SalesGraph graph = new SalesGraph();
    private final JPanel sidePanel = new JPanel();
    private final JLabel genLabel = new JLabel();
    private final JLabel fitnessLabel = new JLabel();

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
        add(graph, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        genLabel.setFont(genLabel.getFont().deriveFont(30f));
        fitnessLabel.setFont(genLabel.getFont().deriveFont(30f));
        sidePanel.add(genLabel);
        sidePanel.add(fitnessLabel);
        setVisible(true);
    }

    public void run() {
        revalidate();
        graph.repaint();
    }

    public void updateFitness(double fitness, double bestFitness) {
        fitnessLabel.setText("Fitness: " + (int) fitness);
        fitnessLabel.setForeground(fitness <= bestFitness ? Color.green : Color.red);
    }

    public void updatePoints(List<SalesAlgorithm.City> cities, int gen) {
        genLabel.setText("Generation: " + gen + "     ");
        graph.updatePoints(cities, gen);
    }
}