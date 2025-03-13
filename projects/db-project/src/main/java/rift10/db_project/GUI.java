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

public class GUI extends JFrame {
    
    private final Database db = Database.getInstance();

    private final JPanel topPanel = new JPanel();
    private final JPanel infoPanel = new JPanel();
    private final JPanel resultsPanel = new JPanel();

    private final JLabel idLabel = new JLabel("Enter your student ID: ");
    private final JTextField idField = new JTextField(20);
    private final JButton idButton = new JButton("Set ID");

    private final JLabel searchLabel = new JLabel("Enter search term: ");
    private final JTextField searchField = new JTextField(20);
    private final JButton searchButton = new JButton("Search");

    private final JLabel infoLabel = new JLabel("Student info will be displayed here");
    private final JLabel currentCreditsLabel = new JLabel();

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

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(infoLabel);
        infoPanel.add(currentCreditsLabel);

        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        scrollPane.setPreferredSize(new Dimension(500, 600));
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        idField.addActionListener((ActionEvent e)  -> addStudentInfo());
        searchField.addActionListener((ActionEvent e)  -> addClassResults());

        idButton.addActionListener((ActionEvent e) -> addStudentInfo());
        searchButton.addActionListener((ActionEvent e) -> addClassResults());

        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
        addClassResults();
    }
    
    private void addStudentInfo() {
        String result = idField.getText().trim();
        studentID = Integer.valueOf(result);
        if (result.isEmpty() || db.getName(studentID) == null) {
            studentID = null;
            infoLabel.setText("Student info will be displayed here");
            addClassResults();
            return;
        }
        infoLabel.setText("Student ID: " + studentID + "       Student name: " + db.getName(studentID) + "       SLC: " + db.getSLC(studentID));
        addClassResults();
    }

    private void addClassResults() {
        resultsPanel.removeAll();
        String search = searchField.getText().trim();
        boolean credits = false;
        if (search.isEmpty()) addResults(db.getAllClasses());
        if (List.of("A", "B", "C", "D", "E", "F", "G", "H", "Z").contains(search.toUpperCase())) {
            addResults(db.getClassesByAG(search));
            credits = true;
        } else if (List.of("AP", "HL", "SL", "P").contains(search.toUpperCase())) {
            addResults(db.getClassesByLevel(search));
        } else addResults(db.getClasses(search));

        if (studentID != null && credits) currentCreditsLabel.setText("       Current " + search.toUpperCase() + " credits: " + db.getStudentCredits(search, studentID));
        else currentCreditsLabel.setText("");
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
            JLabel hasTaken = new JLabel();
            if (studentID != null && db.getClassesTaken(studentID).contains(s)) {
                hasTaken.setText("<html><font size='4' color=red>Already took this class!</font></html>");
            } else hasTaken.setText("");
            hasTaken.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel desc = new JLabel(s.description() + "  Credits: " + s.credits());
            desc.setFont(new Font("Arial", Font.PLAIN, 12));
            result.add(title);
            result.add(hasTaken);
            result.add(desc);
            resultsPanel.add(result);
        }
    }

    private String parenthesize(String text) {
        return " (" + text + ")";
    }
}
