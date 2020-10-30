package br.com.juliogriebeler.pingapp.util;

import java.util.List;

public class ValidationMessageRules {

    public static final List<String> KEYWORDS_ERROR_ICMP = List.of("request timed out", "error", "ping request could not find host");
    public static final List<String> KEYWORDS_ERROR_TRACERT = List.of("unable to resolve target system name", "request timed out");
    public static final List<Integer> ERROR_CODES_TCPIP_PING = List.of(404, 408, 405);
}
