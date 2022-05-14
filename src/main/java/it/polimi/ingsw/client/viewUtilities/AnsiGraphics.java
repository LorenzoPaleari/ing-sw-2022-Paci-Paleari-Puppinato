package it.polimi.ingsw.client.viewUtilities;

import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.internal.CLibrary;

import java.util.Locale;

import static org.fusesource.jansi.internal.CLibrary.ioctl;
import static org.fusesource.jansi.internal.Kernel32.*;

public enum AnsiGraphics
{
    ANSI_RESET ("\u001B[m"),
    ANSI_WHITE("\u001B[37m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_CYAN("\u001B[36m"),

    ANSI_BRIGHT_WHITE("\u001B[97m"),
    ANSI_BRIGHT_RED("\u001B[91m"),
    ANSI_BRIGHT_GREEN("\u001B[92m"),
    ANSI_BRIGHT_YELLOW("\u001B[93m"),
    ANSI_BRIGHT_BLUE("\u001B[94m"),
    ANSI_BRIGHT_PURPLE("\u001B[95m"),
    ANSI_BRIGHT_CYAN("\u001B[96m"),

    ANSI_BG_WHITE("\u001B[47m"),
    ANSI_BG_RED("\u001B[41m"),
    ANSI_BG_GREEN("\u001b[42m"),
    ANSI_BG_YELLOW("\u001B[43m"),
    ANSI_BG_BLUE("\u001B[44m"),
    ANSI_BG_PURPLE("\u001B[45m"),
    ANSI_BG_CYAN("\u001B[46m"),

    STUDENT("\u263A"),
    PROFESSOR("\u263B"),
    MOTHER_NATURE("\u058E"),
    TOWER("\u06E9"),
    COIN("\u20A1"),

    CLEAR("\u001b[1J\u001b[;H"),
    INVERT("\u001b[7m"),
    RESET("\u001B[m");

    private String escape;
    private static int width = 0;
    private static int height = 0;
    private static int firstFreeLine;

    AnsiGraphics(String escape)
    {
        this.escape = escape;
    }

    private static boolean setDimensions(){
        width = AnsiConsole.getTerminalWidth();

        if (System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("win")){
            long console = GetStdHandle(STD_OUTPUT_HANDLE);

            CONSOLE_SCREEN_BUFFER_INFO info = new CONSOLE_SCREEN_BUFFER_INFO();
            GetConsoleScreenBufferInfo(console, info);
            height = info.windowHeight();
            if (height <= 0){
                console = GetStdHandle(STD_ERROR_HANDLE);
                info = new CONSOLE_SCREEN_BUFFER_INFO();
                GetConsoleScreenBufferInfo(console, info);
                height = info.windowHeight();
            }
        } else {
            CLibrary.WinSize sz = new CLibrary.WinSize();
            ioctl(1, CLibrary.TIOCGWINSZ, sz);
            height = sz.ws_row;
            if (height <= 0){
                sz = new CLibrary.WinSize();
                ioctl(2, CLibrary.TIOCGWINSZ, sz);
                height = sz.ws_row;
            }
        }
        return (width > 120);
    }

    public static String getTitle(){
        int rightShift = 1, downShift = 1;
        if (setDimensions())
            rightShift = width/2 - 32;
        if (height >= 18)
            downShift = height/3 - 5;

        System.out.print(CLEAR +""+RESET);
        String center[] = new String[6];
        for (int i = 0; i < 5; i++)
            center[i] = "\u001b["+(downShift + i)+";"+rightShift+"H";
        StringBuilder title = new StringBuilder();
        title.append(center[0] + INVERT+"       "+RESET+"  "+INVERT+"      "+RESET+"   "+INVERT+"  "+RESET+"   "+INVERT+"     "+RESET+"   "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"  "+INVERT+"        "+RESET+"  "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"  "+INVERT+"       "+RESET+"\n");
        title.append(center[1] + INVERT+"  "+RESET+"       "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"      "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"  "+INVERT+"   "+RESET+"  "+INVERT+"  "+RESET+"     "+INVERT+"  "+RESET+"     "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"  "+INVERT+"  "+RESET+"\n");
        title.append(center[2] + INVERT+"       "+RESET+"  "+INVERT+"       "+RESET+"  "+INVERT+"  "+RESET+"  "+INVERT+"       "+RESET+"  "+INVERT+"  "+RESET+" "+INVERT+" "+RESET+" "+INVERT+"  "+RESET+"     "+INVERT+"  "+RESET+"     "+INVERT+"       "+RESET+"  "+INVERT+"       "+RESET+"\n");
        title.append(center[3] + INVERT+"  "+RESET+"       "+INVERT+"  "+RESET+" "+INVERT+"  "+RESET+"    "+INVERT+"  "+RESET+"  "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"  "+INVERT+"  "+RESET+"  "+INVERT+"   "+RESET+"     "+INVERT+"  "+RESET+"          "+INVERT+"  "+RESET+"       "+INVERT+"  "+RESET+"\n");
        title.append(center[4] + INVERT+"       "+RESET+"  "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"  "+INVERT+"  "+RESET+"  "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"  "+INVERT+"  "+RESET+"   "+INVERT+"  "+RESET+"     "+INVERT+"  "+RESET+"     "+INVERT+"       "+RESET+"  "+INVERT+"       "+RESET+"\n");
        return title.toString();
    }

    public static String createMenu(){
        StringBuilder menu = new StringBuilder();

        menu.append(horizontalBar(""));

        for (int i = 0; i < 14; i++)
            menu.append(innerRow("", false));

        //Separator Line
        menu.append(getMenuStart()).append(INVERT).append(" ").append(RESET);
        for (int i = 0; i < 118; i++)
            menu.append("═");
        menu.append(INVERT).append(" ").append(RESET).append("\n");;

        menu.append(chooseLine());

        //Final bar
        menu.append(horizontalBar(""));
        menu.append(cursorReposition());

        return menu.toString();
    }

    public static String setTitle(String s){
        StringBuilder title = new StringBuilder();
        title.append(RESET + "\u001b[").append(height / 3 + 1).append(";H");

        return  "" + title.append(horizontalBar(s));
    }

    public static String putText(String s, boolean error, boolean cleanAll){
        if (cleanAll){
            clean();
            firstFreeLine = height/3 + 2;
        }

        if (firstFreeLine == height/3 + 15)
            clean();

        return "" + innerRow(s, error) + cursorReposition();
    }

    public static void clean(){
        firstFreeLine = height/3 + 3;

        System.out.print("\u001b["+firstFreeLine+";H");
        for(int i = 0; i < 12; i++) {
            System.out.print(innerRow("", false));
        }
    }

    private static StringBuilder innerRow(String s, boolean error){
        StringBuilder row = new StringBuilder();

        if (!s.equals("")) {
            row.append("\u001b[").append(firstFreeLine).append(";H");
            firstFreeLine++;
        }

        row.append(getMenuStart()).append(RESET).append(INVERT).append(" ").append(RESET);
        if (error)
            row.append(ANSI_BRIGHT_RED);
        row.append(s);
        for (int i = 0; i < 118 - s.length(); i++)
            row.append(" ");

        return row.append(RESET).append(INVERT).append(" ").append(RESET).append("\n");
    }

    private static StringBuilder cursorReposition(){
        StringBuilder cursor = new StringBuilder();
        StringBuilder position = new StringBuilder();
        position.append("\u001b[");
        if (height >=24)
            position.append(height / 3 + 17);
        else
            position.append(height - 1);

        cursor.append(position).append(";").append(width / 2 + 70).append("H\u001b[1K");

        return cursor.append(position).append(";H").append(chooseLine()).append(position).append(";").append(width / 2 - 50).append("H").append(ANSI_BRIGHT_GREEN);

    }

    private static StringBuilder horizontalBar(String s){
        StringBuilder horizontalBar = new StringBuilder();

        horizontalBar.append(INVERT).append(getMenuStart()).append(" ").append(s);
        for (int i = 0; i < 119 - s.length(); i++)
            horizontalBar.append(" ");

        return horizontalBar.append(RESET).append("\n");
    }

    private static StringBuilder chooseLine(){
        StringBuilder line = new StringBuilder();
        line.append(getMenuStart()).append(INVERT).append(" ").append(RESET);
        line.append(ANSI_BRIGHT_GREEN).append("INSERT:").append(RESET);
        line.append("\u001b["+111+"C").append(INVERT).append(" ").append(RESET).append("\n");
        return line;
    }

    private static String getMenuStart(){
        if (width > 121)
            return "\u001b["+(width/2 - 60)+"C";

        return "\u001b[m";
    }

    @Override
    public String toString() {
        return escape;
    }

}