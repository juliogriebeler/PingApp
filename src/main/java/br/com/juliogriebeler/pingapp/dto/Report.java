package br.com.juliogriebeler.pingapp.dto;

import java.io.Serializable;

public class Report implements Serializable {

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

    @Override
    public String toString() {
        return String.format("{\"host\":%s, \"icmpPing\":%s, \"tcpPing\":%s, \"trace\":%s}", host, icmpPing, tcpPing, trace);
    }
}
