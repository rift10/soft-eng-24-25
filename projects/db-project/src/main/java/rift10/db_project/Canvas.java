package rift10.db_project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Canvas extends JPanel {

    public Canvas() {
        setFocusable(true); // so we can get key events
        Timer timer = new Timer(17, (ActionEvent e) -> { repaint(); }); // 17 milliseconds is 60fps
        timer.start();
    }

    @Override
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var d = getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        // g.setColor(Color.BLUE);
        // var r = (int)(Math.min(d.width, d.height) * 0.125);
        // var t = System.nanoTime();
        // g.fillOval((d.width - r) / 2, (int)(t / 5e6 % d.height), r, r);
    }
}
