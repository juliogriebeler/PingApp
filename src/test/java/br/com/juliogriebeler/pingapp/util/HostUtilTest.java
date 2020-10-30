package br.com.juliogriebeler.pingapp.util;

import br.com.juliogriebeler.pingapp.exception.InvalidHostException;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

class HostUtilTest {

    private static final String HOST_OK = "jasmin.com";
    private static final String HOST_OK_WITH_HTTP_PREFIX = "http://jasmin.com";
    private static final String HOST_ERROR = "sdgsadg";

    @Test
    void getInetAddressFromHostOk() throws InvalidHostException {
        InetAddress inetAddressResponse = HostUtil.getInetAddressFromHost(HOST_OK);
        assertNotNull(inetAddressResponse);
        assertEquals(inetAddressResponse.getHostName(), HOST_OK);

    }

    @Test
    void getInetAddressFromHostError() {
        assertThrows(InvalidHostException.class, () -> {
            HostUtil.getInetAddressFromHost(HOST_ERROR);
        });
    }

    @Test
    void cleanHttpPrefix() {
        String response = HostUtil.cleanHttpPrefix(HOST_OK_WITH_HTTP_PREFIX);
        assertEquals(HOST_OK, response);
    }

    @Test
    void addHttpPrefix() {
        String response = HostUtil.addHttpPrefix(HOST_OK);
        assertEquals(HOST_OK_WITH_HTTP_PREFIX, response);
    }
}
