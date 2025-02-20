package rift10.db_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassesDB {
    public ClassesDB() {
        // NOTE: Connection and Statement are AutoCloseable.
        //       Don't forget to close them both in order to avoid leaks.
        try(
        // create a database connection
        Connection connection = DriverManager.getConnection("jdbc:sqlite:classes.db");
        Statement statement = connection.createStatement();
        ) {
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        
        statement.executeUpdate("drop table if exists classes");
        statement.executeUpdate("create table if not exists classes (classID integer, name string, AG string, grades integer, slc string, prereq string)");
        statement.executeUpdate("insert into classes values(12345, 'Anna Ray', 9, 'U9')");
        statement.executeUpdate("insert into classes values(23456, 'Bob Peters', 10, 'AMPS')");
        ResultSet rs = statement.executeQuery("select * from classes");
        while(rs.next()) {
            // read the result set
            System.out.println("name = " + rs.getString("name"));
            System.out.println("id = " + rs.getInt("id"));
            System.out.println("grade = " + rs.getInt("grade"));
            System.out.println("slc = " + rs.getString("slc"));
        }
        } catch(SQLException e) {
        // if the error message is "out of memory", 
        // it probably means no database file is found
        e.printStackTrace(System.err);
        }
    }
}