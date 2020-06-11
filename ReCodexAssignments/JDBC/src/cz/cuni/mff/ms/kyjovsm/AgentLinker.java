package cz.cuni.mff.ms.kyjovsm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AgentLinker {
    private static final String countAgentQuery =
            "SELECT COUNT(*) AS COUNT FROM Agents WHERE WORKING_AREA=";
    private static final String countCustomerQuery =
            "SELECT COUNT(*) AS COUNT FROM Customer WHERE WORKING_AREA=";
    private static final String agentsInLocation =
            "SELECT AGENT_NAME, AGENT_CODE FROM Agents WHERE WORKING_AREA=";
    private static final String agentsCustomersPartOne =
            "SELECT CUST_NAME, PHONE_NO FROM Customer WHERE WORKING_AREA=";
    private static final String agentsCustomersPartTwo =
            "AND AGENT_CODE=";

    public int hasLocationAgent( String location,
                                 Connection connection)
            throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(countAgentQuery + "'" +location + "'");
        int countResult = 0;
        if (rs.next()) {
            countResult = rs.getInt("COUNT");
        }
        statement.close();
        return countResult;
    }

    public void linkAgentToCustomer( Connection connection,
                                       String location, int agentNum)
            throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet customersCount = statement.executeQuery(countCustomerQuery + "'" + location + "'");
        printHeader(customersCount, agentNum, location);
        customersCount.close();

        ResultSet agents = statement.executeQuery(agentsInLocation + "'" + location + "'" + "ORDER BY AGENT_NAME" );
        processAgent(agents, connection, location);
    }

    private void printHeader(ResultSet customerCount, int agentNum, String location) throws SQLException {
        int customers = 0;
        if (customerCount.next()) {
            customers = customerCount.getInt("COUNT");
        }
        System.out.printf("%d agents in %s, %d customers",agentNum, location, customers);
        System.out.println();
    }

    private void processAgent(ResultSet agentsInLocation, Connection connection, String location) throws SQLException {
        while (agentsInLocation.next()) {
            String agentName = agentsInLocation.getString("AGENT_NAME");
            String agentCode = agentsInLocation.getString("AGENT_CODE");
            System.out.println("Agent: " + agentName);
            getCustomers(connection, location, agentCode);
        }
    }

    private void getCustomers( Connection connection, String location, String agentCode) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(agentsCustomersPartOne + "'" + location + "'" + agentsCustomersPartTwo + "'" + agentCode + "'" + "ORDER BY CUST_NAME");
        while (resultSet.next()) {
            String customerName = resultSet.getString("CUST_NAME");
            String customerPhone = resultSet.getString("PHONE_NO");
            System.out.printf("\tCustomer: %s, Phone: %s",customerName, customerPhone);
            System.out.println();
        }

    }
}
