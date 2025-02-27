package rift10.db_project;

import java.sql.SQLException;

public class Main {
    private static Database classesDB;
    private static GUI gui;

    public static void main(String[] args) throws SQLException {
        classesDB = new Database();
        gui = new GUI();
        gui.setText(classesDB.classesTest() + classesDB.studentTest());
    }
}

