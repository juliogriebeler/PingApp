package br.com.juliogriebeler.pingapp.util;

import br.com.juliogriebeler.pingapp.annotation.DatabaseColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseUtilTest {

    private List<String> columns;
    @DatabaseColumn(name = "A", type = "B", isPrimaryKey = true, isAutoIncrement = false, isNullable = true, isIndex = true)
    private String dbColumn;

    @BeforeEach
    void setUp() {
        this.columns = List.of("A", "B", "C");
        this.dbColumn = "C";
    }

    @Test
    void constructor(){
        DatabaseUtil databaseUtil = new DatabaseUtil();
        assertNotNull(databaseUtil);
    }

    @Test
    void initDatabase() {
        DatabaseUtil.initDatabase(List.of(this.getClass()));
    }

    @Test
    void getIndexColumns() {
        List<String> columnsResponse = DatabaseUtil.getIndexColumns(this.getClass());
        assertNotNull(columnsResponse);
        assertEquals("A", columnsResponse.get(0));
    }

    @Test
    void getTableColumns() {
        List<String> columnsResponse = DatabaseUtil.getTableColumns(this.getClass());
        assertNotNull(columnsResponse);
        assertEquals("A B PRIMARY KEY  NULL", columnsResponse.get(0));
    }

    @Test
    void buildColumnQuery() throws NoSuchFieldException {
        String response = DatabaseUtil.buildColumnQuery(this.getClass().getDeclaredField("dbColumn").getAnnotation(DatabaseColumn.class));
        assertNotNull(response);
        assertEquals("A B PRIMARY KEY  NULL", response);
    }

    @Test
    void getColumnsAsText() {
        String response = DatabaseUtil.getColumnsAsText(this.columns);
        assertNotNull(response);
        assertEquals("A, B, C", response);
    }
}
