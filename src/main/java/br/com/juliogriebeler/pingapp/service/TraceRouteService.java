package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.runner.CommandLineTaskRunner;
import br.com.juliogriebeler.pingapp.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TraceRouteService implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(TraceRouteService.class);
    private ExecutionArgument executionArgument;

    public TraceRouteService(String host) {
        executionArgument = new ExecutionArgument();
        executionArgument.setHost(host);
    }

    @Override
    public void run() {
        LOGGER.debug(">> TraceRouteService.run");
        try {
            executionArgument.setCommand(Utils.getCommandWithExecutionTypeAndOS(ExecutionType.TRACE_ROUTE));
            executionArgument.setExecutionType(ExecutionType.TRACE_ROUTE);
            new CommandLineTaskRunner().execute(executionArgument);
        } catch (Exception e) {
            LOGGER.error("Error executing TraceRouteService processment {}", e.getLocalizedMessage());
        }
        LOGGER.debug("<< TraceRouteService.run");
    }
}
