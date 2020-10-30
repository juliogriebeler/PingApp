package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.client.HttpClient;
import br.com.juliogriebeler.pingapp.dto.Report;
import br.com.juliogriebeler.pingapp.util.AppConstants;
import br.com.juliogriebeler.pingapp.util.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportService {


    private static final Logger LOGGER = LogManager.getLogger(ReportService.class);

    private HttpClient httpClient;

    public ReportService() {
    }

    public void sendReport(Report report) {
        try {
            String reportApiUrl = AppProperties.getInstance().getValue(AppConstants.REPORT_API_URL);
            this.httpClient = new HttpClient(reportApiUrl);
            this.httpClient.postRequest(report);
        } catch (Exception e) {
            LOGGER.error("Error sending report {} {}", report.toString(), e.getMessage());
        }
    }
}
