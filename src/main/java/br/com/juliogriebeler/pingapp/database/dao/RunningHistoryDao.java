package br.com.juliogriebeler.pingapp.database.dao;

import br.com.juliogriebeler.pingapp.database.connection.DatabaseClient;
import br.com.juliogriebeler.pingapp.database.entity.RunningHistory;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RunningHistoryDao {

    private DatabaseClient dataBaseClient;

    public RunningHistoryDao() {
        dataBaseClient = new DatabaseClient();
    }

    public void createHistory(RunningHistory runningHistory) throws PropertyNotFoundException {
        String query = String.format("INSERT INTO HISTORY (HOST, EXECUTION_TYPE, EXECUTION_RESULT, START_TIME, END_TIME) VALUES ('%s','%s','%s','%s','%s')", runningHistory.getHost(), runningHistory.getExecutionType(), runningHistory.getExecutionResult(), runningHistory.getStartTime(), runningHistory.getEndTime());
        dataBaseClient.executeQueryWithoutResult(query);
    }

    public List<RunningHistory> getAllHistory() throws SQLException, PropertyNotFoundException {
        ResultSet resultSet = dataBaseClient.executeQueryWithResult("SELECT * FROM HISTORY");
        List<RunningHistory> runningHistoryList = new ArrayList<>();
        while (resultSet.next()) {
            RunningHistory runningHistory = new RunningHistory.Builder()
                    .withHost(resultSet.getString("HOST"))
                    .withStartTime(LocalDateTime.ofInstant(resultSet.getDate("START_TIME").toInstant(), ZoneId.systemDefault()))
                    .withEndTime(LocalDateTime.ofInstant(resultSet.getDate("END_TIME").toInstant(), ZoneId.systemDefault()))
                    .build();
            runningHistoryList.add(runningHistory);
        }
        return runningHistoryList;
    }
}
