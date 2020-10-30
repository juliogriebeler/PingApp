package br.com.juliogriebeler.pingapp.service;

import br.com.juliogriebeler.pingapp.client.HttpClient;
import br.com.juliogriebeler.pingapp.dto.GetResponse;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.util.AppConstants;
import br.com.juliogriebeler.pingapp.util.HostUtil;
import br.com.juliogriebeler.pingapp.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpClientService {

    private static final Logger LOGGER = LogManager.getLogger(HttpClientService.class);

    private HttpClient httpClient;

    public HttpClientService() {
    }

    public GetResponse doGetRequest(String url) {
        try {
            this.httpClient = new HttpClient(HostUtil.addHttpPrefix(url));
            int requestTimeout = Utils.getTimeSlotByExecutionType(ExecutionType.PING_TCPIP, AppConstants.TIMEOUT);
            return httpClient.getRequest(requestTimeout);
        } catch (Exception e) {
            LOGGER.error("Error doing get request to url {}: {}", url, e.getLocalizedMessage());
        }
        return null;
    }


}
