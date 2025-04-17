
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SalesGraph extends JPanel {

    private final List<List<Point>> allPoints = new ArrayList<>();
    private int currentGen = 0;
    int index = 0;
    private boolean hasAdded = false;

    public SalesGraph() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.green);
        // setVisible(true);
    }

    public void updatePoints(List<SalesAlgorithm.City> cities, int gen) {
            System.out.println("updating points with gen " + gen);
            // System.out.println("all points: " + allPoints);
            List<Point> points = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                points.add(new Point(cities.get(i).x(), cities.get(i).y()));
            }
            currentGen = gen;
            System.out.println("adding " + points + " to allpoints");
            allPoints.add(points);
            repaint(); 
        SwingUtilities.invokeLater(() -> {
            hasAdded = true;
        });
    }

    public void increaseIndex() {
        index++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("calling paintComponent");
        // System.out.println("paintComponent() on EDT? " + SwingUtilities.isEventDispatchThread());
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // enable better graphics
        g2.setStroke(new BasicStroke(2));

        // System.out.println("allpoints size: " + allPoints.size() + " allpoints is empty: " + allPoints.isEmpty());
        // System.out.println("allpoints index 0 size: " + allPoints.get(0).size() + " is empty: " + allPoints.get(0).isEmpty());
        // if (!hasAdded) {
        //     System.out.println("exiting paint");
        //     return;
        // }

        // List<Point> points = allPoints.get(currentGen);
        List<Point> points = new ArrayList<>();
        points.add(new Point(index * 10, 50)); 
        points.add(new Point(index * 10, 70)); 
        points.add(new Point(index * 10, 60));
        points.forEach(point -> System.out.println(point));
        System.out.println("running paint");

        // Draw lines between points
        g2.setColor(Color.RED);
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Draw points
        g2.setColor(Color.BLACK);
        for (Point p : points) {
            g2.fillOval(p.x - 4, p.y - 4, 8, 8);
        }
    }
}
