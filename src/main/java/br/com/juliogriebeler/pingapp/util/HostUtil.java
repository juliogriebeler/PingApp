package br.com.juliogriebeler.pingapp.util;

import br.com.juliogriebeler.pingapp.exception.InvalidHostException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtil {

    private static final Logger LOGGER = LogManager.getLogger(HostUtil.class);

    public static InetAddress getInetAddressFromHost(String host) throws InvalidHostException {
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            return inetAddress;
        } catch (UnknownHostException e) {
            LOGGER.error("Invalid Host {}: {}", host, e.getLocalizedMessage());
            throw new InvalidHostException(e.getLocalizedMessage());
        }
    }

    /*
    Method to remove host prefix/protocol and white spaces
     */
    public static String cleanHttpPrefix(String host) {
        return host.replaceAll("\\s+", "").replaceFirst("^(http[s]?://)", "").toLowerCase();
    }

    /*
    Method to add HTTP protocol if it already exists not
     */
    public static String addHttpPrefix(String host) {
        return host.contains("^(http[s]?://)") ? host : String.format("%s%s", "http://", host);
    }

}

