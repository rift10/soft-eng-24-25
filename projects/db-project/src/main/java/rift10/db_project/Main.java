package rift10.db_project;

import java.sql.SQLException;

public class Main {
    private static Database database;
    private static GUI gui;

    public static void main(String[] args) throws SQLException {
        database = Database.getInstance();
        gui = new GUI();
        database.initializeDatabase();
        // gui.setText(database.classesTest() + database.studentTest());
        // gui.setText(database.getPossibleClasses("G").toString());
        // gui.setText(database.getClassesTaken(12345).toString());
        // gui.setText(database.getSLC(23456));
    }
}

