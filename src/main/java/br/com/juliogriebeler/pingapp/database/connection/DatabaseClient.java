package br.com.juliogriebeler.pingapp.database.connection;

import br.com.juliogriebeler.pingapp.enumeration.Constants;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;
import br.com.juliogriebeler.pingapp.properties.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DatabaseClient {

    private static final Logger LOGGER = LogManager.getLogger(DatabaseClient.class);

    protected String getConnectionString() throws PropertyNotFoundException {
        return Properties.getInstance().getValue(Constants.DB_CONNECTION);
    }

    public ResultSet executeQueryWithResult(String query) throws PropertyNotFoundException {
        LOGGER.debug(">> DatabaseClient.executeQueryWithResult");
        try (Connection connection = DriverManager.getConnection(getConnectionString())) {
            PreparedStatement stmt = connection.prepareStatement(query);
            return stmt.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("Error starting database connection {}", e.getMessage());
        }
        LOGGER.debug("<< DatabaseClient.openConnection");
        return null;
    }

    public void executeQueryWithoutResult(String query) throws PropertyNotFoundException {
        LOGGER.debug(">> DatabaseClient.executeQueryWithoutResult");
        try (Connection connection = DriverManager.getConnection(getConnectionString())) {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            LOGGER.error("Error starting database connection {}", e.getMessage());
        }
        LOGGER.debug("<< DatabaseClient.executeQueryWithoutResult");
    }
}
