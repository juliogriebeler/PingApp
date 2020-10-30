package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.util.AppConstants;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class HostProcessorService {

    private static final Logger LOGGER = LogManager.getLogger(HostProcessorService.class);

    public HostProcessorService() {
    }

    /*
    Method to process every host for every execution type (based on the ExecutionType enum),
    calling the specific Thread Class set as Service on Enum
     */
    public void processHost(InetAddress host) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(ExecutionType.values().length);
        Stream.of(ExecutionType.values())
                .parallel()
                .forEach(executionType -> {
                    try {
                        Constructor cons = Class.forName(executionType.getService().getName()).getConstructor(InetAddress.class);
                        Runnable runable = (Runnable) cons.newInstance(host);
                        executor.scheduleWithFixedDelay(runable, 1, Utils.getTimeSlotByExecutionType(executionType, AppConstants.DELAY), TimeUnit.SECONDS);
                    } catch (Exception e) {
                        LOGGER.error("Error processing host {}: {}", host, e.getLocalizedMessage());
                    }
                });
    }
}
