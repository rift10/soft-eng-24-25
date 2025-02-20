package rift10.db_project;

import javax.swing.JFrame;


public class Main {
    private ClassesDB classesDB = new ClassesDB();
    private ScheduleDB scheduleDB = new ScheduleDB();
    private StudentDB studentDB = new StudentDB();

    public static final String TITLE = "Course Selector";

    public static void main(String[] args) {
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(400, 400); // Set it to a specific size.
        // Use this if you want your window to fill up the screen.
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Use this to make the frame size to fit the components we put in it.
        // frame.pack()
        // Add an instance of your actual game class here.
        frame.add(new Canvas());
        // Make the frame actually appear.
        frame.setVisible(true);
    }
}

