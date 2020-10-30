package br.com.juliogriebeler.pingapp.runner;

import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.service.CommandLineService;
import br.com.juliogriebeler.pingapp.service.RunningHistoryService;
import br.com.juliogriebeler.pingapp.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.util.Set;

public class TraceRouteRunner implements GenericRunner {

    private static final Logger LOGGER = LogManager.getLogger(TraceRouteRunner.class);
    private ExecutionArgument executionArgument;
    private RunningHistoryService runningHistoryService;

    public TraceRouteRunner(InetAddress host) {
        executionArgument = new ExecutionArgument();
        executionArgument.setHost(host);
        runningHistoryService = new RunningHistoryService();
    }

    @Override
    public void run() {
        LOGGER.debug(">> TraceRouteRunner.run");
        try {
            executionArgument.setCommand(Utils.getCommandWithExecutionTypeAndOS(ExecutionType.TRACE_ROUTE));
            executionArgument.setExecutionType(ExecutionType.TRACE_ROUTE);
            Set<String> executionResponse = new CommandLineService().executeCommand(executionArgument);
            runningHistoryService.persistRunningHistory(executionArgument, executionResponse);
        } catch (Exception e) {
            LOGGER.error("Error executing TraceRouteRunner processment {}", e.getLocalizedMessage());
        }
        LOGGER.debug("<< TraceRouteRunner.run");
    }

    @Override
    public void validateReportSend(Object executionResponse) {
        if (isResponseValid(executionResponse)) {
            this.sendReport(generateReport(executionArgument));
        }
    }

    @Override
    public boolean isResponseValid(Object executionResponse) {
        return false;
    }
}
