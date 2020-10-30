package br.com.juliogriebeler.pingapp.runner;

import br.com.juliogriebeler.pingapp.dao.RunningHistoryDao;
import br.com.juliogriebeler.pingapp.dto.ExecutionArgument;
import br.com.juliogriebeler.pingapp.dto.Report;
import br.com.juliogriebeler.pingapp.entity.RunningHistory;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.service.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.stream.Collectors;

import static br.com.juliogriebeler.pingapp.enumeration.ExecutionType.*;

public interface GenericRunner extends Runnable {

    Logger LOGGER = LogManager.getLogger(GenericRunner.class);
    ReportService reportService = new ReportService();

    void validateReportSend(Object executionResponse);

    boolean isResponseValid(Object executionResponse);

    default Report generateReport(ExecutionArgument executionArgument) {
        RunningHistoryDao runningHistoryDao = new RunningHistoryDao();
        Map<ExecutionType, String> map = runningHistoryDao.getAllHistoryByHost(executionArgument.getHost().getHostName())
                .stream().collect(Collectors.toMap(RunningHistory::getExecutionType, RunningHistory::getExecutionResult));
        Report report = new Report(executionArgument.getHost().getHostName(), map.get(PING_ICMP), map.get(PING_TCPIP), map.get(TRACE_ROUTE));
        LOGGER.warn(report);
        return report;
    }

    default void sendReport(Report report) {
        LOGGER.debug(">> sendReport");
        reportService.sendReport(report);
        LOGGER.debug("<< sendReport");
    }

}
