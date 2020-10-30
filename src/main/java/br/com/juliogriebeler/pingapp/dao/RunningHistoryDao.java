package br.com.juliogriebeler.pingapp.dao;

import br.com.juliogriebeler.pingapp.client.DatabaseClient;
import br.com.juliogriebeler.pingapp.entity.RunningHistory;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.exception.DatabaseConnectionException;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RunningHistoryDao {

    private static final Logger LOGGER = LogManager.getLogger(RunningHistoryDao.class);

    private DatabaseClient databaseClient;

    public RunningHistoryDao() {
        this.databaseClient = new DatabaseClient();
    }

    public void createOrReplaceHistory(RunningHistory runningHistory) throws PropertyNotFoundException, DatabaseConnectionException, IOException {
        String query = String.format("REPLACE INTO HISTORY (HOST, EXECUTION_TYPE, EXECUTION_RESULT, START_TIME, END_TIME) VALUES ('%s','%s','%s','%s','%s')", runningHistory.getHost(), runningHistory.getExecutionType(), runningHistory.getExecutionResult(), runningHistory.getStartTime(), runningHistory.getEndTime());
        this.databaseClient.executeQueryWithoutResult(query);
    }

    public List<RunningHistory> getAllHistoryByHost(String host) {
        List<RunningHistory> runningHistories = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.databaseClient.getConnectionString())) {
            String query = String.format("SELECT * FROM HISTORY WHERE HOST = '%s'", host);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                runningHistories.add(new RunningHistory(
                        resultSet.getLong("ID"),
                        resultSet.getString("HOST"),
                        ExecutionType.valueOf(resultSet.getString("EXECUTION_TYPE")),
                        resultSet.getString("EXECUTION_RESULT"),
                        LocalDateTime.parse(resultSet.getString("START_TIME")),
                        LocalDateTime.parse(resultSet.getString("END_TIME"))
                ));
            }
            return runningHistories;
        } catch (Exception e) {
            LOGGER.error("ERROR HERE {}", e.getLocalizedMessage());
        }
        return null;
    }
}
