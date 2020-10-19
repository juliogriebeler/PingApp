package br.com.juliogriebeler.pingapp.runner;

import br.com.juliogriebeler.pingapp.database.dao.RunningHistoryDao;
import br.com.juliogriebeler.pingapp.database.entity.RunningHistory;
import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommandLineTaskRunner {

    private static final Logger LOGGER = LogManager.getLogger(CommandLineTaskRunner.class);
    private RunningHistoryDao runningHistoryDao;

    public CommandLineTaskRunner() {
        this.runningHistoryDao = new RunningHistoryDao();
    }

    public void execute(ExecutionArgument executionArgument) {
        LOGGER.debug(">> CommandLineTaskRunner.execute");
        List<String> commandExecutionResult = new ArrayList<>();
        try {
            String exec = String.format("%s %s", executionArgument.getCommand(), executionArgument.getHost());
            Process process = Runtime.getRuntime().exec(exec);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                commandExecutionResult.add(line);
            }
            /*
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                // System.exit(0);
            } else {
                //System.exit(1);
            }

             */
            RunningHistory runningHistory = new RunningHistory.Builder()
                    .withHost(executionArgument.getHost())
                    .withExecutionType(executionArgument.getExecutionType())
                    .withExecutionResult(commandExecutionResult.toString())
                    .withStartTime(executionArgument.getStartTime())
                    .withEndTime(LocalDateTime.now())
                    .build();
            this.runningHistoryDao.createHistory(runningHistory);
        } catch (Exception e) {
            LOGGER.error("Error processing {} for host {}: {}", executionArgument.getCommand(), executionArgument.getHost(), e.getLocalizedMessage());
        }
        LOGGER.debug("<< CommandLineTaskRunner.execute");
    }
}
