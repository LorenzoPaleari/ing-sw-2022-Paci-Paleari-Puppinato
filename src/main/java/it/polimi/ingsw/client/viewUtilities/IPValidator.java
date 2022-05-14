package it.polimi.ingsw.client.viewUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPValidator {
    private final static String defaultIP = "127.0.0.1";
    private final static Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(?!$)|$)){4}$");

    public static boolean isCorrectIP(String serverIP){
        Matcher matcher = pattern.matcher(serverIP);
        return matcher.matches();
    }

    public static String getDefaultIP() {
        return defaultIP;
    }
}
