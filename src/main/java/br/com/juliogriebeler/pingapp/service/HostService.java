package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.enumeration.Constants;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;
import br.com.juliogriebeler.pingapp.properties.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class HostService {

    private static final Logger LOGGER = LogManager.getLogger(HostService.class);

    public HostService() {
    }

    public void processInput(String input) {
        Stream.of(input.split(Constants.SEPARATOR))
                .parallel()
                .forEach(host -> processHost(host));
    }

    protected void processHost(String host) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(ExecutionType.values().length);
        Stream.of(ExecutionType.values())
                .parallel()
                .forEach(executionType -> {
                    try {
                        Class clazz = Class.forName(executionType.getService().getName());
                        Constructor cons = clazz.getConstructor(String.class);
                        Runnable runable = (Runnable) cons.newInstance(host);
                        executor.scheduleWithFixedDelay(runable, 1, getDelayByExecutionType(executionType), TimeUnit.SECONDS);
                    } catch (Exception e) {
                        LOGGER.error("Error processing host {}: {}", host, e.getLocalizedMessage());
                    }
                });
    }

    protected Long getDelayByExecutionType(ExecutionType executionType) throws PropertyNotFoundException {
        String key = String.format("%s.%s", executionType.getKey(), Constants.DELAY);
        return Long.parseLong(Properties.getInstance().getValue(key));
    }
}
