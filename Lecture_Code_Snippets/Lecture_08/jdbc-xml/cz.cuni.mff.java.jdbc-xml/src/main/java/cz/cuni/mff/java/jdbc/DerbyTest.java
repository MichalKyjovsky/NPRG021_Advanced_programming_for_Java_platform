package cz.cuni.mff.java.jdbc;

import java.sql.*;

public class DerbyTest {

    public static void main(String[] argv) throws Exception {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        Class.forName(driver);

        Connection con = DriverManager.getConnection("jdbc:derby:derbyDB;create=true");

        Statement stmt = con.createStatement();

        stmt.executeUpdate("CREATE TABLE test (id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(40) NOT NULL)");

        stmt.executeUpdate("INSERT INTO test (name) VALUES ('hello')");

        ResultSet rs = stmt.executeQuery("SELECT * FROM test");

        int i = 1;
        while (rs.next()) {
            System.out.println("Row " + i);
            System.out.println("a = " + rs.getString("id") + ", b = " + rs.getString("name"));
            i++;
        }


        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            if (!ex.getSQLState().equals("XJ015")) {
                throw ex;
            }
        }
    }
}
