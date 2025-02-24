package rift10.db_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDB {
    private final Connection connection;
    private final Statement statement;
    public StudentDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:classes.db");
        statement = connection.createStatement();

        statement.setQueryTimeout(30);  // set timeout to 30 sec.
    }

    public void test() throws SQLException {
        statement.executeUpdate("insert into student values(12345, 'Anna Ray', 9, 'U9')");
        statement.executeUpdate("insert into student values(23456, 'Bob Peters', 10, 'AMPS')");
        ResultSet rs = statement.executeQuery("select * from student");
        while(rs.next()) {
            // read the result set
            System.out.println("name = " + rs.getString("name"));
            System.out.println("id = " + rs.getInt("id"));
            System.out.println("grade = " + rs.getInt("grade"));
            System.out.println("slc = " + rs.getString("slc"));
        }
    }
}
