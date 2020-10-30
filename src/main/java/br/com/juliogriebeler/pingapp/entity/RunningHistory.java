package br.com.juliogriebeler.pingapp.entity;

import br.com.juliogriebeler.pingapp.annotation.DatabaseColumn;
import br.com.juliogriebeler.pingapp.annotation.DatabaseTable;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;

import java.io.Serializable;
import java.time.LocalDateTime;


@DatabaseTable(name = "HISTORY", index = "HISTORY_IDX")
public class RunningHistory implements Serializable {

    @DatabaseColumn(name = "ID", type = "INTEGER", isPrimaryKey = true, isAutoIncrement = true, isNullable = false)
    private Long id;
    @DatabaseColumn(name = "HOST", type = "TEXT", isIndex = true)
    private String host;
    @DatabaseColumn(name = "EXECUTION_TYPE", type = "TEXT", isIndex = true)
    private ExecutionType executionType;
    @DatabaseColumn(name = "EXECUTION_RESULT", type = "TEXT")
    private String executionResult;
    @DatabaseColumn(name = "START_TIME", type = "TEXT")
    private LocalDateTime startTime;
    @DatabaseColumn(name = "END_TIME", type = "TEXT")
    private LocalDateTime endTime;

    public RunningHistory() {
    }

    public RunningHistory(Long id, String host, ExecutionType executionType, String executionResult, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.host = host;
        this.executionType = executionType;
        this.executionResult = executionResult;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public ExecutionType getExecutionType() {
        return executionType;
    }

    public String getExecutionResult() {
        return executionResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
