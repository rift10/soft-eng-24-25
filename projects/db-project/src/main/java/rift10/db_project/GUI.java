package rift10.db_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUI extends JFrame {
    
    private final Database db = Database.getInstance();

    private final JPanel topPanel = new JPanel();
    private final JPanel infoPanel = new JPanel();
    private final JPanel resultsPanel = new JPanel();

    private final JLabel idLabel = new JLabel("Enter your student ID: ");
    private final JTextField idField = new JTextField(20);
    private final JButton idButton = new JButton("Set ID");

    private final JLabel creditLabel = new JLabel("Enter the A-G credit type to search for: ");
    private final JTextField creditField = new JTextField(20);
    private final JButton searchButton = new JButton("Search");

    private final JLabel infoLabel = new JLabel();
    private String studentName;

    public GUI() {
        super("Course Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        topPanel.setLayout(new FlowLayout());
        topPanel.add(idLabel);
        topPanel.add(idField);
        topPanel.add(idButton);

        topPanel.add(creditLabel);
        topPanel.add(creditField);
        topPanel.add(searchButton);

        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(infoLabel);
        infoPanel.setBackground(Color.red);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.green);
        JScrollPane scrollPane = new JScrollPane(resultsPanel);

        idButton.addActionListener((ActionEvent e) -> addStudentInfo());
        searchButton.addActionListener((ActionEvent e) -> addSearchResults());

        add(topPanel, BorderLayout.NORTH);
        // add(infoPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
    }
    
    private void addStudentInfo() {
        infoPanel.removeAll();
        String result = idField.getText().trim();
        if (!result.isEmpty()) {
            Integer id = Integer.valueOf(result);
            studentName = db.getName(id);
            infoLabel.setText("hi");
        }
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void addSearchResults() {
        resultsPanel.removeAll();
        String name = idField.getText().trim();
        if (!name.isEmpty()) {
            for (int i = 1; i <= 5; i++) {
                JPanel result = new JPanel();
                result.setLayout(new BorderLayout());
                result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                result.add(new JLabel("Result " + i + " for " + name), BorderLayout.CENTER);
                resultsPanel.add(result);
            }
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}
