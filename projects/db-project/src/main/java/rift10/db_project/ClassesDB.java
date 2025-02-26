package rift10.db_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassesDB {
    private final Connection connection;
    private final Statement statement;

    private final PreparedStatement insertToClasses;
    private final PreparedStatement insertToStudent;

    public ClassesDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:db.db");
        statement = connection.createStatement();
        statement.setQueryTimeout(30);
        insertToClasses = connection.prepareStatement(
            "insert or ignore into classes values(?, ?, ?, ?)"
        );
        insertToStudent = connection.prepareStatement(
            "insert or ignore into student values(?, ?, ?, ?, ?)"
        );    }

    public String classesTest() throws SQLException {
        String string = new String();
        // statement.executeUpdate("insert or ignore into classes values('aisb1', 'Advanced Math 3', 'C', 'Advanced Math 2')");
        // statement.executeUpdate("insert or ignore into classes values('ao5bm', 'Software Engineering', 'G', 'AP CSA')");
        insertToClasses.setString(1, "aisb1");
        insertToClasses.setString(2, "Advanced Math 3");
        insertToClasses.setString(3, "C");
        insertToClasses.setString(4, "Advanced Math 2");
        // TODO: fix this line it is broken
        ResultSet rs = insertToClasses.executeQuery("select * from classes");
        while(rs.next()) {
            string = string.concat("id = " + rs.getString("classID") + "\n");
            string = string.concat("name = " + rs.getString("name") + "\n");
            string = string.concat("AG credits = " + rs.getString("AG") + "\n");
            string = string.concat("prerequistite = " + rs.getString("prereq") + "\n");
        }
        return string;
    }

    public String studentTest() throws SQLException {
        String string = new String();
        // statement.executeUpdate("insert or ignore into student values(12345, 'Anna Ray', '2/13/10', 2028, 'U9')");
        // statement.executeUpdate("insert or ignore into student values(23456, 'Bob Peters', '6/25/08', 2026, 'AMPS')");
        insertToStudent.setInt(1, 12345);
        insertToStudent.setString(2, "Anna Ray");
        insertToStudent.setString(3, "2/13/10");
        insertToStudent.setInt(4, 2028);
        insertToStudent.setString(5, "U9");
        // TODO: fix this line it is broken
        ResultSet rs = statement.executeQuery("select * from student");
        while(rs.next()) {
            // read the result set
            System.out.println("id = " + rs.getInt("studentId") + "\n");
            System.out.println("name = " + rs.getString("name") + "\n");
            System.out.println("DOB = " + rs.getInt("DOB") + "\n");
            System.out.println("class of = " + rs.getInt("classOf") + "\n");
            System.out.println("slc = " + rs.getString("slc") + "\n");
        }
        return string;
    }
}