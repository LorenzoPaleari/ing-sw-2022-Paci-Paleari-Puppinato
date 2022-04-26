package it.polimi.ingsw.exceptions;

public class NoEntryTilesSetException  extends Exception{
    private String message = "Something went wrong during the set of the entry tile";

    @Override
    public String getMessage() {
        return message;
    }
}
