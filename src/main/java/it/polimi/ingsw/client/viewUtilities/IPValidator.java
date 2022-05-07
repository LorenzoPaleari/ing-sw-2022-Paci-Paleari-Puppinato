package it.polimi.ingsw.client.viewUtilities;

public class IPValidator {
    private final static String defaultIP = "127.0.0.1";

    public static boolean isCorrectIP(String serverIP){
        for (int i = 0; i < serverIP.length(); i++)
            if (!((serverIP.charAt(i) <= '9' && serverIP.charAt(i) >= '0') || serverIP.charAt(i) == '.'))
                return false;

        return true;
    }

    public static String getDefaultIP() {
        return defaultIP;
    }
}
