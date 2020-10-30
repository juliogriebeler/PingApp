package br.com.juliogriebeler.pingapp;

import br.com.juliogriebeler.pingapp.entity.RunningHistory;
import br.com.juliogriebeler.pingapp.service.HostProcessorService;
import br.com.juliogriebeler.pingapp.util.AppConstants;
import br.com.juliogriebeler.pingapp.util.DatabaseUtil;
import br.com.juliogriebeler.pingapp.util.HostUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Stream;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    /*
    Method to start the application, initializing database if it doesn't exist,
    and call the method to validate and process the input array
     */
    public static void main(String[] args) {
        LOGGER.info(">> Starting execution of SimplePingApp");
        List<Class> classList = List.of(RunningHistory.class);
        DatabaseUtil.initDatabase(classList);
        validateAndProcessInput(args);
    }

    /*
    Method to validate the input array and if valid, call the methos to process it.
    If not valid, log message and finish the execution
     */
    protected static void validateAndProcessInput(String[] args) {
        if (null != args && args.length == 1)
            processInput(args[0]);
        else {
            LOGGER.info("Arguments are wrong. Try again!");
            System.exit(1);
        }
    }

    /*
     * Method to parse/split input hosts and process every valid host as a thread in paralell
     */
    protected static void processInput(String input) {
        HostProcessorService hostProcessorService = new HostProcessorService();
        Stream.of(input.split(AppConstants.SEPARATOR))
                .parallel()
                .forEach(host -> {
                    try {
                        hostProcessorService.processHost(HostUtil.getInetAddressFromHost(host.trim()));
                    } catch (Exception e) {
                        LOGGER.error("Error with host {}: {}", host, e.getLocalizedMessage());
                    }
                });
    }

}
