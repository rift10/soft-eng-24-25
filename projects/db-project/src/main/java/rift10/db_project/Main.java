package rift10.db_project;

import java.sql.SQLException;

public class Main {
    private static Database database;
    private static GUI gui;

    public static void main(String[] args) throws SQLException {
        database = new Database();
        gui = new GUI();
        gui.setText(database.classesTest() + database.studentTest());
        // gui.setText(database.getSLC(23456));
    }
}

