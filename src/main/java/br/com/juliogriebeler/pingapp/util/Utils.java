package br.com.juliogriebeler.pingapp.util;

import br.com.juliogriebeler.pingapp.enumeration.ExecutionType;
import br.com.juliogriebeler.pingapp.exception.PropertyNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    private static String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isWindows() {
        return getOS().indexOf("win") >= 0;
    }

    /*
    This method add the OS to the key variable which gets the property from resources file.
    As described on the requeiurement, it validates if the OS is Windows. If not, it consider Linux
    */
    public static String getCommandWithExecutionTypeAndOS(ExecutionType executionType) throws IOException {
        try {
            String key = String.format("%s.%s.%s", executionType.getKey(), AppConstants.COMMAND, Utils.isWindows() ? AppConstants.WINDOWS : AppConstants.LINUX);
            return AppProperties.getInstance().getValue(key);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public static int getTimeSlotByExecutionType(ExecutionType executionType, String timeType) throws PropertyNotFoundException, IOException {
        String key = String.format("%s.%s", executionType.getKey(), timeType);
        return Integer.parseInt(AppProperties.getInstance().getValue(key));
    }

    public static Set<String> parseInputStreamToList(InputStreamReader inputStreamReader) throws IOException {
        Set<String> commandExecutionResult = new LinkedHashSet<>();
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            commandExecutionResult.add(String.format("%s\n", line.trim().toLowerCase()));
        }
        return commandExecutionResult;
    }

    public static boolean isErrorWordPresent(Set<String> executionResponse, List<String> rules) {
        return executionResponse.stream()
                .anyMatch(responseLine -> rules.stream()
                        .anyMatch(rule -> responseLine.contains(rule))
                );
    }

}
