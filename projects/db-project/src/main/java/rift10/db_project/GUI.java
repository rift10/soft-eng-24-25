package rift10.db_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import rift10.db_project.records.Class;

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
    private final JLabel currentCreditsLabel = new JLabel();
    private final JLabel searchLabel = new JLabel("Search results will be displayed here");

    private final JScrollPane scrollPane = new JScrollPane(resultsPanel);

    private Integer studentID;

    public GUI() {
        super("Course Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println(e);
        }
        
        topPanel.setLayout(new FlowLayout());
        topPanel.add(idLabel);
        topPanel.add(idField);
        topPanel.add(idButton);

        topPanel.add(creditLabel);
        topPanel.add(creditField);
        topPanel.add(searchButton);

        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(infoLabel);
        infoPanel.add(currentCreditsLabel);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.add(searchLabel);
        scrollPane.setPreferredSize(new Dimension(400, 600));
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        idButton.addActionListener((ActionEvent e) -> addStudentInfo());
        searchButton.addActionListener((ActionEvent e) -> addSearchResults());

        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
        addSearchResults();
    }
    
    private void addStudentInfo() {
        String result = idField.getText().trim();
        if (result.isEmpty()) return;
        studentID = Integer.valueOf(result);
        infoLabel.setText("Student ID: " + studentID + "    Student name: " + db.getName(studentID) + "    SLC: " + db.getSLC(studentID));
    }

    private void addSearchResults() {
        resultsPanel.removeAll();
        String credit = creditField.getText().trim();
        if (credit.isEmpty()) addResults(db.getAllClasses());
        searchLabel.setText("Searching for: " + credit);
        addResults(db.getPossibleAGClasses(credit));

        if (studentID != null) currentCreditsLabel.setText("     Current " + credit.toUpperCase() + " credits: " + db.getStudentCredits(credit, studentID));
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void addResults(List<Class> results) {
        for (Class s : results) {
            JPanel result = new JPanel();
            result.setLayout(new GridLayout(2, 1));
            result.setBorder(BorderFactory.createLineBorder(Color.gray));
            JLabel title = new JLabel(s.classID() + " " + s.className() + parenthesize(s.level()) + parenthesize(s.AG()));
            title.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel desc = new JLabel(s.description() + " Credits: " + s.credits());
            desc.setFont(new Font("Arial", Font.PLAIN, 12));
            result.add(title);
            result.add(desc);
            resultsPanel.add(result);
        }
    }

    private String parenthesize(String text) {
        return " (" + text + ")";
    }
}
