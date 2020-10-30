package br.com.juliogriebeler.pingapp.enumeration;

import br.com.juliogriebeler.pingapp.runner.PingIcmpRunner;
import br.com.juliogriebeler.pingapp.runner.PingTcpIpRunner;
import br.com.juliogriebeler.pingapp.runner.TraceRouteRunner;

public enum ExecutionType {

    PING_TCPIP("pingtcpip", PingTcpIpRunner.class),
    PING_ICMP("pingicmp", PingIcmpRunner.class),
    TRACE_ROUTE("tracert", TraceRouteRunner.class);

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
