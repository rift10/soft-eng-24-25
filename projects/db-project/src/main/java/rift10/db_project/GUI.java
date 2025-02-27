package rift10.db_project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI extends JFrame {
    
    private final JLabel label = new JLabel();
    private final JButton button = new JButton();
    private final JTextArea textArea = new JTextArea(30, 30);

    public GUI() {
        super("Course Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new FlowLayout());
        
        label.setSize(100, 50);
        label.setText("Course Selector");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea); // Add scroll bars if needed

        button.addActionListener((ActionEvent e) -> {
            if (e.getSource() == button) {
                // TODO: do stuff
            }
        });
        
        add(scrollPane, BorderLayout.CENTER);
        add(label, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void setText(String string) {
        textArea.setText(string);
    }
}
