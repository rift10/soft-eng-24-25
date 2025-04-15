
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class SalesGraph extends JPanel {

    private static SalesGraph instance = null;
    public static SalesGraph getInstance() {
        if (instance == null) instance = new SalesGraph();
        return instance;
    }

    private final List<List<Point>> allPoints = new ArrayList<>();
    private int currentGen = 0;

    public SalesGraph() {
        setLayout(new FlowLayout());
        setBackground(Color.green);
        setVisible(true);
    }

    public void periodic() {
        allPoints.forEach((point) -> System.out.print("index: \"" + allPoints.indexOf(point) + "\""));
        repaint();
    }

    public void updatePoints(List<SalesAlgorithm.City> cities, int gen) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            points.add(new Point(cities.get(i).x(), cities.get(i).y()));
        }
        currentGen = gen;
        allPoints.add(points);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // enable better graphics
        g2.setStroke(new BasicStroke(2));
        // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // if (allPoints.size() < currentGen - 1) return;
        // Draw lines between points
        List<Point> points = allPoints.get(currentGen);
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

    // public void periodic() {
    //     if (character.getCurrentKeyCode() != 0) System.out.println("current key code: " + character.getCurrentKeyCode());
    //     // label.setText("is player touching food: " + character.isTouchingFood());
    //     // System.out.println("is player touching food: " + character.isTouchingFood());
    //     // System.out.println("score: " + character.getScore());
    //     // label.setText(food.getDX() + ", " + food.getDY());

    //     xTemp += 0.1;
    //     yTemp += 0.1;
    //     repaint();
    // }

    // @Override
    // public void paintComponent(Graphics g) {
    //     label.setText("painting component");
    //     super.paintComponent(g);
    //     // drawBackground(g);
    //     draw(g);
    // }

    // public void drawBackground(Graphics g) {
    //     g.setColor(Color.WHITE);
    //     g.fillRect(0, 0, Main.ENVIRONMENT_WIDTH, Main.ENVIRONMENT_HEIGHT);
    // }

    // public void draw(Graphics g) {
    //     // label.setText("drawing sprites");
    //     Graphics2D g2d = (Graphics2D) g;
    //     g2d.drawImage(character.getImage(), character.getX(), character.getY(), this);
    //     g2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
    // }
}
