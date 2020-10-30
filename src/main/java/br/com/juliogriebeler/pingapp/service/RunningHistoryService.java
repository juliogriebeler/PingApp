package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.dao.RunningHistoryDao;
import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.entity.RunningHistory;
import br.com.juliogriebeler.pingapp.exception.DatabaseConnectionException;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

public class RunningHistoryService {

    private RunningHistoryDao runningHistoryDao;

    public RunningHistoryService() {
        this.runningHistoryDao = new RunningHistoryDao();
    }

    public void persistRunningHistory(ExecutionArgument executionArgument, Set<String> commandExecutionResult) throws PropertyNotFoundException, DatabaseConnectionException, IOException {
        RunningHistory runningHistory = new RunningHistory(
                null,
                executionArgument.getHost().getHostName(),
                executionArgument.getExecutionType(),
                commandExecutionResult.toString(),
                executionArgument.getStartTime(),
                LocalDateTime.now());
        this.runningHistoryDao.createOrReplaceHistory(runningHistory);
    }
}
