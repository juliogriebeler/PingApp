package br.com.juliogriebeler.pingapp.enumeration;

import br.com.juliogriebeler.pingapp.service.PingIcmpService;
import br.com.juliogriebeler.pingapp.service.PingTcpIpService;
import br.com.juliogriebeler.pingapp.service.TraceRouteService;

public enum ExecutionType {

    PING_TCPIP("pingtcpip", PingTcpIpService.class),
    PING_ICMP("pingicmp", PingIcmpService.class),
    TRACE_ROUTE("tracert", TraceRouteService.class);

    private final String key;
    private final Class service;

    ExecutionType(String key, Class service) {
        this.key = key;
        this.service = service;
    }

    public Class getService() {
        return service;
    }

    public String getKey() {
        return key;
    }
}
