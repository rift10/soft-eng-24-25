package rift10.db_project.reference;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tests {

    private final PreparedStatement selectFromClasses = null;
    private final PreparedStatement selectFromCourses = null;
    private final PreparedStatement selectFromStudent = null;

    public String classTest() {
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

public String courseTest() {
    try {
        String string = new String();
        ResultSet rs = selectFromCourses.executeQuery();
        while(rs.next()) {
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
