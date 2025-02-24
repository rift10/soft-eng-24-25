package rift10.db_project;

import java.sql.SQLException;

import javax.swing.JFrame;


public class Main {
    private static ClassesDB classesDB;
    private static ScheduleDB scheduleDB;
    private static StudentDB studentDB;

    public static final String TITLE = "Course Selector";

    public static void main(String[] args) throws SQLException {
        // this is hella cooked but whatever
        // im pretty sure these shouldn't be static
        classesDB = new ClassesDB();
        scheduleDB = new ScheduleDB();
        studentDB = new StudentDB();

        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(400, 400); // Set it to a specific size.
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Use this if you want your window to fill up the screen.
        // frame.pack() // Use this to make the frame size to fit the components we put in it.
        frame.add(new Canvas());
        frame.setVisible(true);
    }
}

