package br.com.juliogriebeler.pingapp.runner;

import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.dto.GetResponse;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.service.HttpClientService;
import br.com.juliogriebeler.pingapp.service.RunningHistoryService;
import br.com.juliogriebeler.pingapp.util.Utils;
import br.com.juliogriebeler.pingapp.util.ValidationMessageRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.util.Set;

public class PingTcpIpRunner implements GenericRunner {

    private static final Logger LOGGER = LogManager.getLogger(PingTcpIpRunner.class);
    private ExecutionArgument executionArgument;
    private RunningHistoryService runningHistoryService;

    public PingTcpIpRunner(InetAddress host) {
        executionArgument = new ExecutionArgument();
        executionArgument.setHost(host);
        this.runningHistoryService = new RunningHistoryService();
    }

    @Override
    public void run() {
        LOGGER.debug(">> PingTcpIpRunner.run");
        try {
            executionArgument.setCommand(Utils.getCommandWithExecutionTypeAndOS(ExecutionType.PING_TCPIP));
            executionArgument.setExecutionType(ExecutionType.PING_TCPIP);
            GetResponse executionResponse = new HttpClientService().doGetRequest(executionArgument.getHost().getHostName());
            this.runningHistoryService.persistRunningHistory(executionArgument, Set.of(executionResponse.toString()));
            validateReportSend(executionResponse);
        } catch (Exception e) {
            LOGGER.error("Error executing PingTcpIpRunner processment {}", e.getLocalizedMessage());
        }
        LOGGER.debug("<< PingTcpIpRunner.run");

    }

    @Override
    public void validateReportSend(Object executionResponse) {
        if (!isResponseValid(executionResponse))
            sendReport(generateReport(executionArgument));
    }

    @Override
    public boolean isResponseValid(Object executionResponse) {
        return !ValidationMessageRules.ERROR_CODES_TCPIP_PING.contains(((GetResponse) executionResponse).getCode());
    }

}
