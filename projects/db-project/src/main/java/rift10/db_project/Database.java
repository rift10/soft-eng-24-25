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
    private static Database instance = null;
    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
     }
    private Connection connection;
    private Statement statement;

    private PreparedStatement insertToClasses;
    private PreparedStatement insertToStudent;
    private PreparedStatement insertToCourse;
    private PreparedStatement selectFromClasses;
    private PreparedStatement selectFromStudent;
    private PreparedStatement selectUniqueStudent;
    private PreparedStatement selectClassesWithCredits;
    private PreparedStatement selectClassesTaken;

    private Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            insertToClasses = connection.prepareStatement(
                "insert or ignore into class values(?, ?, ?, ?, ?)"
            );
            insertToStudent = connection.prepareStatement(
                "insert or ignore into student values(?, ?, ?, ?, ?)"
            );
            insertToCourse = connection.prepareStatement(
                "insert or ignore into course values(?, ?, ?)"
            );
            selectFromClasses = connection.prepareStatement(
                "select * from class"
            );
            selectFromStudent = connection.prepareStatement(
                "select * from student"
            );
            selectUniqueStudent = connection.prepareStatement(
                "select * from student where studentID like (?)"
            );
            selectClassesWithCredits = connection.prepareStatement(
                "select * from class where AG like (?)"
            );
            selectClassesTaken = connection.prepareStatement(
                "select * from class join course on class.classID = course.classID where course.studentID = (?) and class.AG like (?)"
            );
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /** Returns the amount of credits a student has of a specific type */
    public int getStudentCredits(String agType, int studentID) {
        try {
            // TODO
            int result = 0;
            selectClassesTaken.setInt(1, studentID);
            selectClassesTaken.setString(2, agType);
            ResultSet rs = selectClassesTaken.executeQuery();
            while (rs.next()) {
                result += rs.getInt("credits");
            }
            return result;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return 0;
    }

    /** Returns the classes taken by a specified student */
    public List<String> getClassesTaken(int studentID) {
        // TODO retest
        try {
            var result = new ArrayList<String>();
            selectClassesTaken.setInt(1, studentID);
            selectClassesTaken.setString(2, "*");
            ResultSet rs = selectClassesTaken.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("className"));
            }
            return result;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return List.of("");
    }

    /** Returns the possible classes a student can take that fulfill a certain credit type */
    public List<String> getPossibleClasses(String agType) {
        try {
            var result = new ArrayList<String>();
            selectClassesWithCredits.setString(1, agType);
            ResultSet rs = selectClassesWithCredits.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("className"));
            }
            return result;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return List.of("");
    }

    /** Returns a student's full name */
    public String getName(int studentID) {
        try {
            selectUniqueStudent.setInt(1, studentID);
            return selectUniqueStudent.executeQuery().getString("name");
        } catch(SQLException e) {
            System.err.println(e);
        }
        return "";
    }

    /** Returns a student's small learning community */
    public String getSLC(int studentID) {
        try {
            selectUniqueStudent.setInt(1, studentID);
            return selectUniqueStudent.executeQuery().getString("slc");
        } catch(SQLException e) {
            System.err.println(e);
        }
        return "";
    }

    public void initializeDatabase() {
        insertClass("aisb1", "Advanced Math 3", "C", 20, "Advanced Math 2");
        insertClass("ao5bm", "Software Engineering", "G", 20, "AP CSA");

        insertStudent(12345, "Anna Ray", "2/13/10", 2028, "U9");
        insertStudent(23456, "Bob Peters", "6/25/08", 2026, "AMPS");

        insertCourse("ao5bm", 12345, 6);
        insertCourse("aisb1", 23456, 3);
    }

    public void insertClass(String classCode, String className, String agType, int creditAmount, String prereq) {
        try {
            insertToClasses.setString(1, classCode);
            insertToClasses.setString(2, className);
            insertToClasses.setString(3, agType);
            insertToClasses.setInt(4, creditAmount);
            insertToClasses.setString(5, prereq);
            insertToClasses.execute();
        } catch(SQLException e) {
            System.err.println(e);
        }
    }

    public void insertStudent(int studentID, String name, String dob, int classOf, String slc) {
        try {
            insertToStudent.setInt(1, studentID);
            insertToStudent.setString(2, name);
            insertToStudent.setString(3, dob);
            insertToStudent.setInt(4, classOf);
            insertToStudent.setString(5, slc);
            insertToStudent.execute();
        } catch(SQLException e) {
            System.err.println(e);
        }
    }

    public void insertCourse(String classCode, int studentID, int period) {
        try {
            insertToCourse.setString(1, classCode);
            insertToCourse.setInt(2, studentID);
            insertToCourse.setInt(3, period);
            insertToCourse.execute();
        } catch(SQLException e) {
            System.err.println(e);
        }
    }

    public String classesTest() {
        try {
            String string = new String();
            ResultSet rs = selectFromClasses.executeQuery();
            while(rs.next()) {
                string = string.concat("id = " + rs.getString("classID") + "\n");
                string = string.concat("name = " + rs.getString("className") + "\n");
                string = string.concat("AG credits = " + rs.getString("AG") + "\n");
                string = string.concat("prerequistite = " + rs.getString("prereq") + "\n");
            }
            return string;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return "";
    }

    public String studentTest() {
        try {
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
        } catch(SQLException e) {
            System.err.println(e);
        }
        return "";
    }
}