import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
    
    private static final String BUFFER = "     ";
    private final JLabel graphics = new JLabel();
    private final JPanel sidePanel = new JPanel();
    private final JLabel genLabel = new JLabel();
    private final JLabel fitnessLabel = new JLabel();
    private final JLabel bestFitnessLabel = new JLabel();

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
        bestFitnessLabel.setFont(genLabel.getFont().deriveFont(30f));
        sidePanel.add(genLabel);
        sidePanel.add(fitnessLabel);
        sidePanel.add(bestFitnessLabel);
        graphics.setFont(genLabel.getFont().deriveFont(30f));
        graphics.setText(BUFFER + "Loading gen 0...");
        setVisible(true);
    }

    private void run() {
        revalidate();
        graphics.repaint();
    }

    public void updateLoad(int gen) {
        graphics.setText(BUFFER + (gen > ImageAlgorithm.NUM_GENS ? "Complete" : "Loading Gen " + gen + "..."));
    }

    public void updateFitness(int gen, int fitness, int bestFitness) {
        genLabel.setText("Generation: " + gen);
        fitnessLabel.setText("Fitness: " + fitness);
        bestFitnessLabel.setText("Best Fitness: " + bestFitness + BUFFER);
        fitnessLabel.setForeground(fitness <= bestFitness ? Color.green : Color.red);
    }

    public void updateImage(BufferedImage image) {
        double xScalar = (double) getWidth() / image.getWidth();
        double yScalar = (double) getHeight() / image.getHeight();
        double scalar = Math.min(xScalar, yScalar) * 0.9;
        graphics.setIcon(new ImageIcon(scale(image, scalar)));
    }

    private static BufferedImage scale(BufferedImage before, double scale) {
        int w = before.getWidth();
        int h = before.getHeight();
        // Create a new image of the proper size
        int w2 = (int) (w * scale);
        int h2 = (int) (h * scale);
        BufferedImage after = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_ARGB);
        AffineTransformOp scaleOp = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), AffineTransformOp.TYPE_BILINEAR);
        scaleOp.filter(before, after);
        return after;
    }
}