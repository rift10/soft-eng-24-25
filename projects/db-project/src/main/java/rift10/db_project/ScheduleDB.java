package rift10.db_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScheduleDB {
    private final Connection connection;
    private final Statement statement;
    public ScheduleDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:classes.db");
        statement = connection.createStatement();

        statement.setQueryTimeout(30);  // set timeout to 30 sec.
    }

    public void test() throws SQLException {
        statement.executeUpdate("insert into schedule values(12345, '.', '1876bh', 'asiub', '1872h', '12876b', '12igvy1', '1279yb', '.', '.')");
        statement.executeUpdate("insert into schedule values(23456, '.', '128736', 'wdwiy', '12873', '1287gd', 'sdiy712', '12iuiy', '.', '.')");
        ResultSet rs = statement.executeQuery("select * from schedule");
        while(rs.next()) {
            // read the result set
            System.out.println("student id = " + rs.getString("studentID"));
            System.out.println("0 period = " + rs.getString("zeroID"));
            System.out.println("1st period = " + rs.getString("firstID"));
            System.out.println("2nd period = " + rs.getString("secondID"));
        }
    }
}