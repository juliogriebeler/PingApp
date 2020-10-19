package br.com.juliogriebeler.pingapp.dto;

import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;

import java.time.LocalDateTime;

public class ExecutionArgument {

    private ExecutionType executionType;
    private String command;
    private String host;
    private LocalDateTime startTime;

    public ExecutionArgument(){
        this.startTime = LocalDateTime.now();
    }

    public ExecutionType getExecutionType() {
        return executionType;
    }

    public void setExecutionType(ExecutionType executionType) {
        this.executionType = executionType;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
