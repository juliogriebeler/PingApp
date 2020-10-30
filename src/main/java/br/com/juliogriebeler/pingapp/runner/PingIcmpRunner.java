package br.com.juliogriebeler.pingapp.runner;

import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.service.CommandLineService;
import br.com.juliogriebeler.pingapp.service.RunningHistoryService;
import br.com.juliogriebeler.pingapp.util.Utils;
import br.com.juliogriebeler.pingapp.util.ValidationMessageRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.util.Set;

public class PingIcmpRunner implements GenericRunner {

    private static final Logger LOGGER = LogManager.getLogger(PingIcmpRunner.class);
    private ExecutionArgument executionArgument;
    private RunningHistoryService runningHistoryService;

    public PingIcmpRunner(InetAddress host) {
        executionArgument = new ExecutionArgument();
        executionArgument.setHost(host);
        this.runningHistoryService = new RunningHistoryService();
    }

    @Override
    public void run() {
        LOGGER.debug(">> PingIcmpRunner.run");
        try {
            executionArgument.setCommand(Utils.getCommandWithExecutionTypeAndOS(ExecutionType.PING_ICMP));
            executionArgument.setExecutionType(ExecutionType.PING_ICMP);
            Set<String> executionResponse = new CommandLineService().executeCommand(executionArgument);
            runningHistoryService.persistRunningHistory(executionArgument, executionResponse);
            validateReportSend(executionResponse);
        } catch (Exception e) {
            LOGGER.error("Error executing PingIcmpRunner processment {}", e.getLocalizedMessage());
        }
        LOGGER.debug("<< PingIcmpRunner.run");
    }

    @Override
    public void validateReportSend(Object executionResponse) {
        if (Utils.isErrorWordPresent((Set<String>) executionResponse, ValidationMessageRules.KEYWORDS_ERROR_ICMP) && !isResponseValid(executionResponse)) {
            this.sendReport(generateReport(executionArgument));
        }
    }

    @Override
    public boolean isResponseValid(Object executionResponse) {
        return ((Set<String>) executionResponse).stream().anyMatch(s ->
                s.startsWith("packets") && Integer.parseInt(s.substring(s.indexOf("("), s.indexOf("%"))) == 0
        );
    }
}
