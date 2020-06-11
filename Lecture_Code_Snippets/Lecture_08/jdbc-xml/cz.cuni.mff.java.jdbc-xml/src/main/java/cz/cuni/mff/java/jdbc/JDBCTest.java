package cz.cuni.mff.java.jdbc;

import java.sql.*;

public class JDBCTest {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test?serverTimezone=UTC", "", "");

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM test");

        int i = 1;
        while (rs.next()) {
            System.out.println("Row " + i);
            System.out.println("a = " + rs.getString("id") + ", b = " + rs.getString("name"));
            i++;
        }

        stmt.executeUpdate("INSERT INTO test (name) VALUES (\"hello\")");

        stmt.close();
        con.close();
    }
}
