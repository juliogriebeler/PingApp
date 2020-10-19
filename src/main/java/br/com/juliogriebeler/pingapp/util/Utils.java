package br.com.juliogriebeler.pingapp.util;

import br.com.juliogriebeler.pingapp.enumeration.Constants;
import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;
import br.com.juliogriebeler.pingapp.properties.Properties;

import java.io.IOException;

public class Utils {

    private static String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isWindows() {
        return getOS().indexOf("win") >= 0;
    }

    /*
    This method add the OS to the key variable which gets the property from resources file.
    As described on the requeiurement, it only validates if the OS is Windows. If not, it consider Linux
    */
    public static String getCommandWithExecutionTypeAndOS(ExecutionType executionType) throws PropertyNotFoundException, IOException {
        try {
            String key = String.format("%s.%s.%s", executionType.getKey(), Constants.COMMAND, Utils.isWindows() ? Constants.WINDOWS : Constants.LINUX);
            String command = Properties.getInstance().getValue(key);
            return command;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
