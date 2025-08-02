package rift10.db_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

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
    private PreparedStatement selectClassesWithLevel;
    private PreparedStatement selectClassesTaken;
    private PreparedStatement selectClassesTakenWithAG;
    private PreparedStatement searchClass;
    private PreparedStatement selectFromCourse;

    private Faker faker = new Faker();
    private Random random = new Random();

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
            selectFromCourse = connection.prepareStatement(
                "select * from course"
            );
            selectClassesWithCredits = connection.prepareStatement(
                "select * from class where AG like (?)"
            );
            selectClassesWithLevel = connection.prepareStatement(
                "select * from class where level like (?)"
            );
            searchClass = connection.prepareStatement(
                "select * from class where classID like (?) or className like (?) or description like (?)"
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

    /** Returns a list of Class records of all the classes in the database */
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

    /** Returns the possible classes a student can take that fulfill a certain credit type */
    public List<Class> getClassesByAG(String agType) {
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

    /** Returns the classes of a certain level */
    public List<Class> getClassesByLevel(String level) {
        try {
            var result = new ArrayList<Class>();
            selectClassesWithLevel.setString(1, level);
            ResultSet rs = selectClassesWithLevel.executeQuery();
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
    public List<Class> getClasses(String searchTerm) {
        try {
            var result = new ArrayList<Class>();
            searchClass.setString(1, "%" + searchTerm + "%");
            searchClass.setString(2, "%" + searchTerm + "%");
            searchClass.setString(3, "%" + searchTerm + "%");
            ResultSet rs = searchClass.executeQuery();
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

    public void initializeDatabase() {
        List<String> codes = parseFileToList("/workspaces/rift10/projects/db-project/src/main/java/rift10/db_project/data/codes.txt");
        List<String> courses = parseFileToList("/workspaces/rift10/projects/db-project/src/main/java/rift10/db_project/data/courses.txt");
        for (int i = 0; i < codes.size(); i++) {
            insertClass(codes.get(i), courses.get(i), getAG(codes.get(i)), getLevel(courses.get(i)), getCredits(codes.get(i)), "", faker.lorem().sentence());
        }

        for (int i = 1; i < 100; i++) {
            int birthYear = random(2005, 2010);
            insertStudent(
                i,
                faker.name().firstName() + " " + faker.name().lastName(),
                randomBirthday(birthYear),
                birthYear + 18,
                randomSLC()
            );
        }

        List<Class> allClasses = getAllClasses();

        for (int j = 1; j < 100; j++) {
            for (int i = 1; i < 7; i++) {
                insertCourse(allClasses.get(random(0, allClasses.size() - 1)).classID(), j, i);
            }
        }
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

    private int random(int start, int end) {
        return random.nextInt(end - start + 1) + start;
    }

    private String randomBirthday(int birthday) {
        return random(1, 31) + "/" + random(1, 12) + "/" + birthday;
    }

    private String randomSLC() {
        return switch(random(1, 10)) {
            case 1 -> "AHA";
            case 2 -> "AMPS";
            case 3, 4 -> "BIHS";
            case 5 -> "CAS";
            case 6 -> "IS";
            case 7 -> "U9";
            default -> "AC";
        };
    }

    private String getAG(String classCode) {
        if (classCode.substring(0, 1).equals("W")) return "E";
        if (classCode.substring(1, 2).equals("J") || classCode.substring(0, 2).equals("HG")) return "F";
        if (List.of("S", "N", "L").contains(classCode.substring(1, 2))) return "G";
        if (classCode.substring(1, 2).equals("M")) return "H";
        return classCode.substring(1, 2);
    }

    private String getLevel(String className) {
        if (className.substring(0, 2).equals("AP") || className.contains("AP-")) return "AP";
        if (className.contains("HL")) return "HL";
        if (className.contains("SL")) return "SL";
        return "P";
    }

    private int getCredits(String classCode) {
        return classCode.substring(classCode.length() - 1).equals("Y") ? 10 : 5;
    }

    private static List<String> parseFileToList(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println(e);
        }
        return List.of("");
    }

    public String courseTest() {
        try {
            String string = new String();
            ResultSet rs = selectFromCourse.executeQuery();
            while(rs.next()) {
                // read the result set
                string = string.concat("class id = " + rs.getString("classID") + "\n");
                string = string.concat("student id = " + rs.getInt("studentID") + "\n");
                string = string.concat("period = " + rs.getInt("period") + "\n");
            }
            return string;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return "";
    }
}