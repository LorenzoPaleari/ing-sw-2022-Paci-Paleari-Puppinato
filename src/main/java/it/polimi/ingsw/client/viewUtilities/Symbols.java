package it.polimi.ingsw.client.viewUtilities;

public enum Symbols
{
    STUDENT("\u263A"),
    PROFESSOR("\u263B"),
    MOTHER_NATURE("\u058E"),
    TOWER("\u06E9"),
    COIN("\u20A1");

    static final String RESET = "\u001B[0m";

    private String escape;


    Symbols(String escape)
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