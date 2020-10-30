package br.com.juliogriebeler.pingapp.dao;

import br.com.juliogriebeler.pingapp.client.DatabaseClient;
import br.com.juliogriebeler.pingapp.exception.DatabaseConnectionException;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;
import br.com.juliogriebeler.pingapp.util.DatabaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class GenericDao {

    private static final Logger LOGGER = LogManager.getLogger(GenericDao.class);
    private DatabaseClient dataBaseClient;

    public GenericDao() {
        this.dataBaseClient = new DatabaseClient();
    }

    public void createDbTable(String tablename, List<String> columns) throws PropertyNotFoundException, DatabaseConnectionException, IOException {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS ");
        query.append(tablename);
        query.append("(");
        query.append(DatabaseUtil.getColumnsAsText(columns));
        query.append(")");
        LOGGER.debug("CreateDbTable query: {}", query.toString());
        this.dataBaseClient.executeQueryWithoutResult(query.toString());
    }

    public void createDbIndex(String indexName, String tableName, List<String> columns) throws PropertyNotFoundException, DatabaseConnectionException, IOException {
        StringBuilder query = new StringBuilder();
        query.append("CREATE UNIQUE INDEX IF NOT EXISTS ");
        query.append(indexName);
        query.append(" ON ");
        query.append(tableName);
        query.append("(");
        query.append(DatabaseUtil.getColumnsAsText(columns));
        query.append(")");
        LOGGER.debug("CreateDbIndex query: {}", query.toString());
        this.dataBaseClient.executeQueryWithoutResult(query.toString());
    }


}
