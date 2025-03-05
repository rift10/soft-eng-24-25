package rift10.db_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final Connection connection;
    private final Statement statement;

    private final PreparedStatement insertToClasses;
    private final PreparedStatement insertToStudent;
    private final PreparedStatement selectFromClasses;
    private final PreparedStatement selectFromStudent;
    private final PreparedStatement selectUniqueStudent;
    private final PreparedStatement selectClassesWithCredits;

    public Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:db.db");
        statement = connection.createStatement();
        statement.setQueryTimeout(30);
        insertToClasses = connection.prepareStatement(
            "insert or ignore into classes values(?, ?, ?, ?)"
        );
        insertToStudent = connection.prepareStatement(
            "insert or ignore into student values(?, ?, ?, ?, ?)"
        );
        selectFromClasses = connection.prepareStatement(
            "select * from classes"
        );
        selectFromStudent = connection.prepareStatement(
            "select * from student"
        );
        selectUniqueStudent = connection.prepareStatement(
            "select * from student where studentID like (?)"
        );
        selectClassesWithCredits = connection.prepareStatement(
            "select * from classes join course on classes.classID = course.classID where classes.AG like (?)"
        );
    }

    /** Returns a student's small learning community */
    public String getSLC(int studentID) throws SQLException {
        selectUniqueStudent.setInt(1, studentID);
        return selectUniqueStudent.executeQuery().getString("slc");
    }

    /** Returns the amount of credits a student has of a specific type */
    public String getStudentCredits(String ag, int studentID) throws SQLException {
        // TODO
        return "";
    }

    /** Returns the possible classes a student can take that fulfill a certain credit type */
    public List<String> getPossibleClasses(String ag) throws SQLException {
        // TODO: test
        var result = new ArrayList<String>();
        selectClassesWithCredits.setString(1, ag);
        ResultSet rs = selectClassesWithCredits.executeQuery();
        while (rs.next()) {
            result.add(rs.getString("className"));
        }
        return result;
    }

    public String classesTest() throws SQLException {
        String string = new String();
        insertToClasses.setString(1, "aisb1");
        insertToClasses.setString(2, "Advanced Math 3");
        insertToClasses.setString(3, "C");
        insertToClasses.setString(4, "Advanced Math 2");
        insertToClasses.execute();
        insertToClasses.setString(1, "ao5bm");
        insertToClasses.setString(2, "Software Engineering");
        insertToClasses.setString(3, "G");
        insertToClasses.setString(4, "AP CSA");
        insertToClasses.execute();
        ResultSet rs = selectFromClasses.executeQuery();
        while(rs.next()) {
            string = string.concat("id = " + rs.getString("classID") + "\n");
            string = string.concat("name = " + rs.getString("className") + "\n");
            string = string.concat("AG credits = " + rs.getString("AG") + "\n");
            string = string.concat("prerequistite = " + rs.getString("prereq") + "\n");
        }
        return string;
    }

    public String studentTest() throws SQLException {
        String string = new String();
        insertToStudent.setInt(1, 12345);
        insertToStudent.setString(2, "Anna Ray");
        insertToStudent.setString(3, "2/13/10");
        insertToStudent.setInt(4, 2028);
        insertToStudent.setString(5, "U9");
        insertToStudent.execute();

        insertToStudent.setInt(1, 23456);
        insertToStudent.setString(2, "Bob Peters");
        insertToStudent.setString(3, "6/25/08");
        insertToStudent.setInt(4, 2026);
        insertToStudent.setString(5, "AMPS");
        insertToStudent.execute();
        ResultSet rs = selectFromStudent.executeQuery();
        while(rs.next()) {
            // read the result set
            string = string.concat("id = " + rs.getInt("studentId") + "\n");
            string = string.concat("name = " + rs.getString("name") + "\n");
            string = string.concat("DOB = " + rs.getString("DOB") + "\n");
            string = string.concat("class of = " + rs.getInt("classOf") + "\n");
            string = string.concat("slc = " + rs.getString("slc") + "\n");
        }
        return string;
    }
}