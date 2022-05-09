package it.polimi.ingsw.client.viewUtilities;

public enum Colors
{
    ANSI_RESET ("\u001B"),
    ANSI_WHITE("\u001B"),
    ANSI_RED("\u001B"),
    ANSI_GREEN("\u001B"),
    ANSI_YELLOW("\u001B"),
    ANSI_BLUE("\u001B"),
    ANSI_PURPLE("\u001B"),
    ANSI_CYAN("\u001B"),
    ANSI_BLACK("\u001B"),

    ANSI_BRIGHT_WHITE("\u001B"),
    ANSI_BRIGHT_RED("\u001B"),
    ANSI_BRIGHT_GREEN("\u001B"),
    ANSI_BRIGHT_YELLOW("\u001B"),
    ANSI_BRIGHT_BLUE("\u001B"),
    ANSI_BRIGHT_PURPLE("\u001B"),
    ANSI_BRIGHT_CYAN("\u001B"),
    ANSI_BRIGHT_BLACK("\u001B"),

    ANSI_BG_WHITE("\u001B"),
    ANSI_BG_RED("\u001B"),
    ANSI_BG_YELLOW("\u001B"),
    ANSI_BG_BLUE("\u001B"),
    ANSI_BG_PURPLE("\u001B"),
    ANSI_BG_CYAN("\u001B"),
    ANSI_BG_BLACK("\u001B");

    static final String RESET = "\u001B[0m";

    private String escape;


    Colors(String escape)
    {
        this.escape = escape;
    }


    public String getEscape()
    {
        return escape;
    }


    @Override
    public String toString()
    {
        return escape;
    }
}