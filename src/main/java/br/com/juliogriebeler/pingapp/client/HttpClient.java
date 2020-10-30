package br.com.juliogriebeler.pingapp.client;

import br.com.juliogriebeler.pingapp.dto.GetResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpClient {

    private static final Logger LOGGER = LogManager.getLogger(HttpClient.class);
    private URL requestUrl;

    public HttpClient(String requestUrl) throws MalformedURLException {
        this.requestUrl = new URL(requestUrl);
    }

    public void postRequest(Object jsonInputObject) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                byte[] input = ((String) jsonInputObject).getBytes(Charset.defaultCharset());
                outputStream.write(input, 0, input.length);
            }
        } catch (IOException e) {
            LOGGER.error("Error doing post request to {}: {}", requestUrl, e.getLocalizedMessage());
        }
    }

    public GetResponse getRequest(int timeout) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection("GET");
            httpURLConnection.setConnectTimeout(timeout * 1000);
            String responseMessage = httpURLConnection.getResponseMessage();
            int code = httpURLConnection.getResponseCode();
            return new GetResponse(code, responseMessage);
        } catch (Exception e) {
            LOGGER.error("Error doing get request to {} : {}", requestUrl, e.getLocalizedMessage());
            return new GetResponse(-1, e.getMessage());
        }
    }

    protected HttpURLConnection getHttpURLConnection(String protocol) throws IOException {
        HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
        con.setRequestMethod(protocol);
        con.setDoOutput(true);
        return con;
    }
}
