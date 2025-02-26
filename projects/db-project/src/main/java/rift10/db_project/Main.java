package rift10.db_project;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import rift10.db_project.reference.Canvas;


public class Main {
    private static ClassesDB classesDB;

    public static final String TITLE = "Course Selector";

    public static void main(String[] args) throws SQLException {
        // this is hella cooked but whatever
        // im pretty sure these shouldn't be static
        classesDB = new ClassesDB();

        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(400, 400); // Set it to a specific size.
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Use this if you want your window to fill up the screen.
        // frame.pack() // Use this to make the frame size to fit the components we put in it.

        JTextArea textArea = new JTextArea(100, 50);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(classesDB.classesTest() + classesDB.studentTest());

        JScrollPane scrollPane = new JScrollPane(textArea); // Add scroll bars if needed

        frame.setLayout(new FlowLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(new Canvas());
        frame.setVisible(true);
    }
}

