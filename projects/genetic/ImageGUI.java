import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ImageGUI extends JFrame {
    
    private final JLabel graphics = new JLabel();
    private final JPanel sidePanel = new JPanel();
    private final JLabel genLabel = new JLabel();
    private final JLabel fitnessLabel = new JLabel();

    public ImageGUI() {
        super("Image Evolution");
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
        add(graphics, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        genLabel.setFont(genLabel.getFont().deriveFont(30f));
        fitnessLabel.setFont(genLabel.getFont().deriveFont(30f));
        sidePanel.add(genLabel);
        sidePanel.add(fitnessLabel);
        graphics.setFont(genLabel.getFont().deriveFont(30f));
        graphics.setText("    Loading First Gen...");
        setVisible(true);
    }

    public void run() {
        revalidate();
        graphics.repaint();
    }

    public void updateFitness(double fitness, double bestFitness) {
        fitnessLabel.setText("Fitness: " + (int) fitness);
        fitnessLabel.setForeground(fitness <= bestFitness ? Color.green : Color.red);
    }

    public void updateImage(BufferedImage image, int gen) {
        genLabel.setText("Generation: " + gen + "     ");
        graphics.setIcon(new ImageIcon(image));
    }

    public void setImageSize(int width, int height) {
        graphics.setSize(width, height);
    }
}