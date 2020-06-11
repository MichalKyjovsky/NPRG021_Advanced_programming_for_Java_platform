package cz.cuni.mff.java.orm;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelloWorld {

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:~/H2DBTest", "sa", "");
            if (args[0].equals("C")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("create table TEST(id int identity, name varchar)");
                stm.close();
            } else if (args[0].equals("I")){
                Statement stm = conn.createStatement();
                stm.executeUpdate("insert into TEST (name) values ('Petr')");
                stm.close();
            } else {
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("select * from TEST");
                while (rs.next()) {
                    System.out.printf("id: '%d', name: '%s'\n", rs.getInt("id"), rs.getString("name"));
                }
                stm.close();
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelloWorld.class.getName()).log(Level.SEVERE,"SQLException", ex);
        }
    }
}
