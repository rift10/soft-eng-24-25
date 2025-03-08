package rift10.db_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rift10.db_project.records.Class;

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
    private PreparedStatement selectUniqueStudent;
    private PreparedStatement selectClassesWithCredits;
    private PreparedStatement selectClassesTaken;
    private PreparedStatement selectClassesTakenWithAG;

    private Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            insertToClasses = connection.prepareStatement(
                "insert or ignore into class values(?, ?, ?, ?, ?, ?, ?)"
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
            selectUniqueStudent = connection.prepareStatement(
                "select * from student where studentID like (?)"
            );
            selectClassesWithCredits = connection.prepareStatement(
                "select * from class where AG like (?)"
            );
            selectClassesTaken = connection.prepareStatement(
                "select * from class join course on class.classID = course.classID where course.studentID = (?)"
            );
            selectClassesTakenWithAG = connection.prepareStatement(
                "select * from class join course on class.classID = course.classID where course.studentID = (?) and class.AG like (?)"
            );
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /** Returns the amount of credits a student has of a specific type */
    public int getStudentCredits(String agType, int studentID) {
        try {
            int result = 0;
            selectClassesTakenWithAG.setInt(1, studentID);
            selectClassesTakenWithAG.setString(2, agType);
            ResultSet rs = selectClassesTakenWithAG.executeQuery();
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
    public List<Class> getClassesTaken(int studentID) {
        try {
            var result = new ArrayList<Class>();
            selectClassesTaken.setInt(1, studentID);
            ResultSet rs = selectClassesTaken.executeQuery();
            while (rs.next()) {
                result.add(sqlToClassRecord(rs));
            }
            return result;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    /** Returns the possible classes a student can take that fulfill a certain credit type */
    public List<Class> getPossibleAGClasses(String agType) {
        try {
            var result = new ArrayList<Class>();
            selectClassesWithCredits.setString(1, agType);
            ResultSet rs = selectClassesWithCredits.executeQuery();
            while (rs.next()) {
                result.add(sqlToClassRecord(rs));
            }
            return result;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return null;
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

    public List<Class> getAllClasses() {
        try {
            var result = new ArrayList<Class>();
            ResultSet rs = selectFromClasses.executeQuery();
            while (rs.next()) {
                result.add(sqlToClassRecord(rs));
            }
            return result;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public void initializeDatabase() {
        insertClass("AC96Y", "Advanced Math 3", "C", "H", 10, "Advanced Math 2", "Covers pre calculus topics such as polar graphing, conic sections, and advanced trigonometry");
        insertClass("AD20Y", "AP Biology", "D", "AP", 10, "Chemistry", "This class prepares students for the AP Biology test and explores topics surrounding cellular processes and genetic adaptations");
        insertClass("AJ50Y", "Chorus", "F", "P", 10, "", "Students learn many different songs over the course of the year in preparation for a concert at the end of the semester to showcase their skills");
        insertClass("IA15Y", "IB-HL-English 1", "B", "IB", 10, "IHS Global Literature", "This class covers how to analyze different forms of media, including novels, poetry, and films");
        insertClass("IB09S", "IHS-Theory of Knowledge", "A", "IB", 5, "", "Teaches students how to think critically about the world around them");
        insertClass("AS54Y", "Robotics Build Advanced", "G", "P", 10, "", "Students learn how to design, fabricate, wire, and code a robot");
        insertClass("AS46Y", "Software Engineering: Advanced Topic CS", "G", "P", 10, "AP CSA", "This class teaches advanced software engineering topics that prepare students for careers in computer science");
        insertClass("WE84Y", "Spanish IV", "E", "P", 10, "Spanish III", "This class reviews all tenses of Spanish in preparation for AP Spanish");

        insertStudent(12345, "Anna Ray", "2/13/10", 2028, "U9");
        insertStudent(23456, "Bob Peters", "6/25/08", 2026, "AMPS");
        insertStudent(34567, "Caitlin Hughes", "7/18/09", 2027, "AC");

        insertCourse("AS46Y", 12345, 6);
        insertCourse("AC96Y", 23456, 3);
    }

    public void insertClass(String classCode, String className, String agType, String level, int creditAmount, String prereq, String desc) {
        try {
            insertToClasses.setString(1, classCode);
            insertToClasses.setString(2, className);
            insertToClasses.setString(3, agType);
            insertToClasses.setString(4, level);
            insertToClasses.setInt(5, creditAmount);
            insertToClasses.setString(6, prereq);
            insertToClasses.setString(7, desc);
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

    private Class sqlToClassRecord(ResultSet rs) {
        try {
            return new Class(rs.getString("classID"), rs.getString("className"), rs.getString("AG"), rs.getString("level"), rs.getInt("credits"), rs.getString("prereq"), rs.getString("description"));
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    // public record Course(String classID, int studentID, int period) {}
    // public record Student(int studentID, String name, String DOB, int classOf, String SLC) {}
}