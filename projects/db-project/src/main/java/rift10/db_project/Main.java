package rift10.db_project;

import java.sql.SQLException;

public class Main {
    private static Database db;
    private static GUI gui;

    public static void main(String[] args) throws SQLException {
        db = Database.getInstance();
        db.initializeDatabase();
        gui = new GUI();
    }
}

