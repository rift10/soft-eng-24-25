
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class SalesGraph extends JPanel {

    private final List<List<Point>> allPoints = Collections.synchronizedList(new ArrayList<>());
    private int currentGen = 0;

    public SalesGraph() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.green);
    }

    public void updatePoints(List<SalesAlgorithm.City> cities, int gen) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            points.add(new Point(cities.get(i).x(), cities.get(i).y()));
        }
        currentGen = gen;
        allPoints.add(points);
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // enable better graphics
        g2.setStroke(new BasicStroke(2));

        if (allPoints.isEmpty()) return;
        List<Point> points = allPoints.get(currentGen);
    
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
