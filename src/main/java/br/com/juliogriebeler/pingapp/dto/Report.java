package br.com.juliogriebeler.pingapp.dto;

public class Report {

    private String host;
    private String icmpPing;
    private String tcpPing;
    private String trace;

    public Report(String host, String icmpPing, String tcpPing, String trace) {
        this.host = host;
        this.icmpPing = icmpPing;
        this.tcpPing = tcpPing;
        this.trace = trace;
    }

    public String getHost() {
        return host;
    }

    public String getIcmpPing() {
        return icmpPing;
    }

    public String getTcpPing() {
        return tcpPing;
    }

    public String getTrace() {
        return trace;
    }
}
