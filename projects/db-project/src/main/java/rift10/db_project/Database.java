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
    private final PreparedStatement insertToCourse;
    private final PreparedStatement selectFromClasses;
    private final PreparedStatement selectFromStudent;
    private final PreparedStatement selectUniqueStudent;
    private final PreparedStatement selectClassesWithCredits;
    private final PreparedStatement selectClassesTaken;

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
        insertToCourse = connection.prepareStatement(
            "insert or ignore into course values(?, ?, ?)"
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
            "select * from classes where AG like (?)"
        );
        selectClassesTaken = connection.prepareStatement(
            "select * from classes join course on classes.classID = course.classID where course.studentID = (?) and class.AG like (?)"
        );
    }

    /** Returns the amount of credits a student has of a specific type */
    public int getStudentCredits(String agType, int studentID) throws SQLException {
        // TODO
        int result = 0;
        selectClassesTaken.setInt(1, studentID);
        selectClassesTaken.setString(2, agType);
        ResultSet rs = selectClassesTaken.executeQuery();
        while (rs.next()) {
            result += rs.getInt("credits");
        }
        return 0;
    }

    /** Returns the classes taken by a specified student */
    public List<String> getClassesTaken(int studentID) throws SQLException {
        // TODO retest
        var result = new ArrayList<String>();
        selectClassesTaken.setInt(1, studentID);
        selectClassesTaken.setString(2, "*");
        ResultSet rs = selectClassesTaken.executeQuery();
        while (rs.next()) {
            result.add(rs.getString("className"));
        }
        return result;
    }

    /** Returns the possible classes a student can take that fulfill a certain credit type */
    public List<String> getPossibleClasses(String agType) throws SQLException {
        var result = new ArrayList<String>();
        selectClassesWithCredits.setString(1, agType);
        ResultSet rs = selectClassesWithCredits.executeQuery();
        while (rs.next()) {
            result.add(rs.getString("className"));
        }
        return result;
    }

    /** Returns a student's small learning community */
    public String getSLC(int studentID) throws SQLException {
        selectUniqueStudent.setInt(1, studentID);
        return selectUniqueStudent.executeQuery().getString("slc");
    }

    public void initializeDatabase() throws SQLException {
        insertClass("aisb1", "Advanced Math 3", "C", "Advanced Math 2");
        insertClass("ao5bm", "Software Engineering", "G", "AP CSA");

        insertStudent(12345, "Anna Ray", "2/13/10", 2028, "U9");
        insertStudent(23456, "Bob Peters", "6/25/08", 2026, "AMPS");

        insertCourse("ao5bm", 12345, 6);
        insertCourse("aisb1", 23456, 3);
    }

    public void insertClass(String classCode, String className, String agType, String prereq) throws SQLException {
        insertToClasses.setString(1, classCode);
        insertToClasses.setString(2, className);
        insertToClasses.setString(3, agType);
        insertToClasses.setString(4, prereq);
        insertToClasses.execute();
    }

    public void insertStudent(int studentID, String name, String dob, int classOf, String slc) throws SQLException {
        insertToStudent.setInt(1, studentID);
        insertToStudent.setString(2, name);
        insertToStudent.setString(3, dob);
        insertToStudent.setInt(4, classOf);
        insertToStudent.setString(5, slc);
        insertToStudent.execute();
    }

    public void insertCourse(String classCode, int studentID, int period) throws SQLException {
        insertToCourse.setString(1, classCode);
        insertToCourse.setInt(2, studentID);
        insertToCourse.setInt(3, period);
        insertToCourse.execute();
    }

    public String classesTest() throws SQLException {
        String string = new String();
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