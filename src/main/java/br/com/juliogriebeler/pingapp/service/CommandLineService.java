package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.exception.CommandLineExecutionException;
import br.com.juliogriebeler.pingapp.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStreamReader;
import java.util.Set;

public class CommandLineService {

    private static final Logger LOGGER = LogManager.getLogger(CommandLineService.class);

    public CommandLineService() {
    }

    public Set<String> executeCommand(ExecutionArgument executionArgument) throws CommandLineExecutionException {
        LOGGER.debug(">> CommandLineService.execute");
        try {
            String exec = String.format("%s %s", executionArgument.getCommand(), executionArgument.getHost().getHostName());
            Process process = Runtime.getRuntime().exec(exec);
            return Utils.parseInputStreamToList(new InputStreamReader(process.getInputStream()));
        } catch (Exception e) {
            LOGGER.error("Error processing command {} for host {}: {}", executionArgument.getCommand(), executionArgument.getHost(), e.getLocalizedMessage());
            throw new CommandLineExecutionException(e.getMessage(), e.getCause());
        } finally {
            LOGGER.debug("<< CommandLineService.execute");
        }
    }


}
