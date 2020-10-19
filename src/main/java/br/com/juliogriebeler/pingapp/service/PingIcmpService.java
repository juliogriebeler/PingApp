package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.runner.CommandLineTaskRunner;
import br.com.juliogriebeler.pingapp.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingIcmpService implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(PingIcmpService.class);
    private ExecutionArgument executionArgument;

    public PingIcmpService(String host) {
        executionArgument = new ExecutionArgument();
        executionArgument.setHost(host);
    }

    @Override
    public void run() {
        LOGGER.debug(">> PingIcmpService.run");
        try {
            executionArgument.setCommand(Utils.getCommandWithExecutionTypeAndOS(ExecutionType.PING_ICMP));
            executionArgument.setExecutionType(ExecutionType.PING_ICMP);
            new CommandLineTaskRunner().execute(executionArgument);
        } catch (Exception e) {
            LOGGER.error("Error executing PingIcmpService processment {}", e.getLocalizedMessage());
        }
        LOGGER.debug("<< PingIcmpService.run");
    }
}
