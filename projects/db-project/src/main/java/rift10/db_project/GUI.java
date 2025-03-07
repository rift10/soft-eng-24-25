package rift10.db_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;

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

    private final JLabel infoLabel = new JLabel("Student info will be displayed here");
    private final JLabel searchLabel = new JLabel("Search results will be displayed here");

    private final JScrollPane scrollPane = new JScrollPane(resultsPanel);

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

        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.add(searchLabel);
        scrollPane.setPreferredSize(new Dimension(400, 600));

        idButton.addActionListener((ActionEvent e) -> addStudentInfo());
        searchButton.addActionListener((ActionEvent e) -> addSearchResults());

        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
    }
    
    private void addStudentInfo() {
        String result = idField.getText().trim();
        if (!result.isEmpty()) {
            Integer id = Integer.valueOf(result);
            infoLabel.setText("Student ID: " + result + "    Student name: " + db.getName(id) + "    SLC: " + db.getSLC(id));
        }
    }

    private void addSearchResults() {
        resultsPanel.removeAll();
        String credit = creditField.getText().trim();
        if (credit.isEmpty()) {
            searchLabel.setText("Please enter a name to search.");
            return;
        }
        searchLabel.setText("Searching for: " + credit);
        List<String> results = db.getPossibleClasses(credit);
        for (String s : results) {
            JPanel result = new JPanel();
            result.setLayout(new BorderLayout());
            result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            result.add(new JLabel(s));
            resultsPanel.add(result);
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}
