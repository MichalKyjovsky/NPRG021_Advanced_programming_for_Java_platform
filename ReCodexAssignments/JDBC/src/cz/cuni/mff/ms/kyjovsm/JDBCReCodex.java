package cz.cuni.mff.ms.kyjovsm;

import java.sql.*;

public class JDBCReCodex {

    private static final String H2_MEM_URL = "jdbc:h2:mem:default";

    /**
     * Instantiate the H2 in-memory database and load it with the schema and data from provided scripts.
     *
     * The database will stay in the memory and keep its content until JVM is terminated.
     *
     * @param schemaScript sql script containing ddl commands to create database schema
     * @param dataScript sql script containing commands to insert data into tables
     * @throws SQLException sql exception
     */
    private static void prepareDatabase(String schemaScript, String dataScript) throws SQLException, Exception {
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection(
                String.format("%s;DB_CLOSE_DELAY=-1;INIT=runscript from '%s'\\;runscript from '%s'",
                        H2_MEM_URL, schemaScript, dataScript));
        con.close();
    }

    public static void main(String[] args) throws SQLException {

        String schemaScript = args[0];
        String dataScript = args[1];
        String location = args[2];
        try {
            prepareDatabase(schemaScript, dataScript);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(H2_MEM_URL);
        AgentLinker agentLinker = new AgentLinker();
        int numberOfAgentsOnLocation = agentLinker.hasLocationAgent(location, connection);

        if (numberOfAgentsOnLocation > 0) {
            agentLinker.linkAgentToCustomer(connection, location, numberOfAgentsOnLocation);
        } else {
            System.out.println("No agents in " + location);
        }
    }
}