package br.com.juliogriebeler.pingapp.database.entity;

import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;

import java.time.LocalDateTime;

public class RunningHistory {

    private Long id;
    private String host;
    private ExecutionType executionType;
    private String executionResult;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public RunningHistory(Long id, String host, ExecutionType executionType, String executionResult, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.host = host;
        this.executionType = executionType;
        this.executionResult = executionResult;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static class Builder {
        private Long id;
        private String host;
        private ExecutionType executionType;
        private String executionResult;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public Builder() {
        }

        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        public Builder withExecutionResult(String executionResult) {
            this.executionResult = executionResult;
            return this;
        }

        public Builder withExecutionType(ExecutionType executionType) {
            this.executionType = executionType;
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public RunningHistory build() {
            return new RunningHistory(id, host, executionType, executionResult, startTime, endTime);
        }
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
