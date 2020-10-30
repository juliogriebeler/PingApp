package br.com.juliogriebeler.pingapp.client;

import br.com.juliogriebeler.pingapp.exception.DatabaseConnectionException;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;
import br.com.juliogriebeler.pingapp.util.AppConstants;
import br.com.juliogriebeler.pingapp.util.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseClient {

    private static final Logger LOGGER = LogManager.getLogger(DatabaseClient.class);

    public String getConnectionString() throws PropertyNotFoundException, IOException {
        return AppProperties.getInstance().getValue(AppConstants.DB_CONNECTION);
    }

    public void executeQueryWithoutResult(String query) throws PropertyNotFoundException, DatabaseConnectionException, IOException {
        LOGGER.debug(">> DatabaseClient.executeQueryWithoutResult");
        try (Connection connection = DriverManager.getConnection(getConnectionString())) {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            LOGGER.error("Error executing query {} with method executeQueryWithoutResult {}", query, e.getMessage());
            throw new DatabaseConnectionException(String.format("Error executing query %s: %s", query, e.getMessage()), e);
        }
        LOGGER.debug("<< DatabaseClient.executeQueryWithoutResult");
    }
}
