package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.runner.CommandLineTaskRunner;
import br.com.juliogriebeler.pingapp.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingTcpIpService implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(PingTcpIpService.class);
    private ExecutionArgument executionArgument;

    public PingTcpIpService(String host) {
        executionArgument = new ExecutionArgument();
        executionArgument.setHost(host);
    }

    @Override
    public void run() {
        LOGGER.debug(">> PingTcpIpService.run");
        try {
            executionArgument.setCommand(Utils.getCommandWithExecutionTypeAndOS(ExecutionType.PING_TCPIP));
            executionArgument.setExecutionType(ExecutionType.PING_TCPIP);
            new CommandLineTaskRunner().execute(executionArgument);
        } catch (Exception e) {
            LOGGER.error("Error executing PingTcpIpService processment {}", e.getLocalizedMessage());
        }
        LOGGER.debug("<< PingTcpIpService.run");

    }
}
