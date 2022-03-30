package it.polimi.ingsw.exceptions;

public class NoEntryTilesSetException  extends Exception{
    private String message;
    public NoEntryTilesSetException (String s) {
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
