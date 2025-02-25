package rift10.db_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassesDB {
    private final Connection connection;
    private final Statement statement;
    public ClassesDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:db.db");
        statement = connection.createStatement();

        statement.setQueryTimeout(30);  // set timeout to 30 sec.
    }

    public String test() throws SQLException {
        String string = new String();
        statement.executeUpdate("insert or ignore into classes values('aisb1', 'Advanced Math 3', 'C', 'Advanced Math 2')");
        statement.executeUpdate("insert or ignore into classes values('ao5bm', 'Software Engineering', 'G', 'AP CSA')");
        ResultSet rs = statement.executeQuery("select * from classes");
        while(rs.next()) {
            string = string.concat("id = " + rs.getString("classID") + "\n");
            string = string.concat("name = " + rs.getString("name") + "\n");
            string = string.concat("AG credits = " + rs.getString("AG") + "\n");
            string = string.concat("prerequistite = " + rs.getString("prereq") + "\n");
        }
        return string;
    }
}