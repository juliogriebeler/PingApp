package br.com.juliogriebeler.pingapp;

import br.com.juliogriebeler.pingapp.database.dao.GenericDao;
import br.com.juliogriebeler.pingapp.service.HostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info(">> Starting execution of SimplePingApp");
        initDatabase();
        HostService hostService = new HostService();
        if (null != args && args.length == 1) {
            hostService.processInput(args[0]);
        } else {
            LOGGER.info("Arguments are wrong. Try again!");
            System.exit(1);
        }
    }

    private static void initDatabase() {
        LOGGER.debug(">> initDatabase");
        try {
            GenericDao genericDao = new GenericDao();
            List<String> columnsList = new ArrayList<>();
            columnsList.add("ID INTEGER PRIMARY KEY AUTOINCREMENT");
            columnsList.add("HOST TEXT NOT NULL");
            columnsList.add("EXECUTION_TYPE TEXT NOT NULL");
            columnsList.add("EXECUTION_RESULT TEXT NOT NULL");
            columnsList.add("START_TIME TEXT NOT NULL");
            columnsList.add("END_TIME TEXT NOT NULL");
            genericDao.createDbTable("HISTORY", columnsList);
            LOGGER.debug("Database initialized succesfully");
        } catch (Exception e) {
            LOGGER.error("Error initialing database: {}", e.getMessage());
        }
        LOGGER.debug("<< initDatabase");
    }
}
