package br.com.juliogriebeler.pingapp.service;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class HostProcessorServiceTest {

    private HostProcessorService hostProcessorService;

    @Test
    void processHost() throws UnknownHostException {
        String host = "jasmin.com";
        InetAddress inetAddress = InetAddress.getByName(host);
        hostProcessorService.processHost(inetAddress);

    }
}
