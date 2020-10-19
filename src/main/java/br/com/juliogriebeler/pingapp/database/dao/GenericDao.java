package br.com.juliogriebeler.pingapp.database.dao;

import br.com.juliogriebeler.pingapp.database.connection.DatabaseClient;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;

import java.sql.SQLException;
import java.util.List;

public class GenericDao {

    private DatabaseClient dataBaseClient;

    public GenericDao() {
        this.dataBaseClient = new DatabaseClient();
    }

    public void createDbTable(String tablename, List<String> columns) throws SQLException, PropertyNotFoundException {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS ");
        query.append(tablename);
        query.append("(");
        columns.stream()
                .limit(columns.size()-1)
                .map(column -> String.format("%s, ", column))
                .forEach(column -> query.append(column));
        query.append(columns.get(columns.size()-1));
        query.append(")");
        this.dataBaseClient.executeQueryWithoutResult(query.toString());
    }
}
