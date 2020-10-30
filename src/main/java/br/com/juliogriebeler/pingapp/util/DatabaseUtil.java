package br.com.juliogriebeler.pingapp.util;

import br.com.juliogriebeler.pingapp.annotation.DatabaseColumn;
import br.com.juliogriebeler.pingapp.annotation.DatabaseTable;
import br.com.juliogriebeler.pingapp.dao.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseUtil {

    private static final Logger LOGGER = LogManager.getLogger(DatabaseUtil.class);

    /*
    Method to start the file based database.
    The classes in the list must be annotated with DataBaseTable
    and the fields must be annotated with DatabaseColumn.
     */
    public static void initDatabase(List<Class> classList) {
        LOGGER.debug(">> initDatabase");
        try {
            GenericDao genericDao = new GenericDao();
            for (Class aClass : classList) {
                DatabaseTable databaseTable = (DatabaseTable) aClass.getAnnotation(DatabaseTable.class);
                genericDao.createDbTable(databaseTable.name(), getTableColumns(aClass));
                genericDao.createDbIndex(databaseTable.index(), databaseTable.name(), getIndexColumns(aClass));
            }
            LOGGER.debug("Database initialized succesfully");
        } catch (Exception e) {
            LOGGER.error("Error initialing database: {}", e.getMessage());
        }
        LOGGER.debug("<< initDatabase");
    }

    protected static List<String> getIndexColumns(Class c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.getAnnotation(DatabaseColumn.class) != null)
                .filter(field -> field.getAnnotation(DatabaseColumn.class).isIndex())
                .map(field -> field.getAnnotation(DatabaseColumn.class).name())
                .collect(Collectors.toList());
    }

    protected static List<String> getTableColumns(Class c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.getAnnotation(DatabaseColumn.class) != null)
                .map(field -> buildColumnQuery(field.getAnnotation(DatabaseColumn.class)))
                .collect(Collectors.toList());
    }

    protected static String buildColumnQuery(DatabaseColumn databaseColumn) {
        StringBuilder columnItem = new StringBuilder();
        columnItem.append(String.format(" %s %s %s %s %s ",
                databaseColumn.name(),
                databaseColumn.type(),
                databaseColumn.isPrimaryKey() ? "PRIMARY KEY " : "",
                databaseColumn.isNullable() ? "NULL " : "",
                databaseColumn.isAutoIncrement() ? "AUTOINCREMENT " : ""));
        return columnItem.toString().trim();
    }

    public static String getColumnsAsText(List<String> columns) {
        StringBuilder columnList = new StringBuilder();
        columns.stream()
                .limit(columns.size() - 1)
                .map(column -> String.format("%s, ", column))
                .forEach(column -> columnList.append(column));
        columnList.append(columns.get(columns.size() - 1));
        return columnList.toString().trim();
    }
}
