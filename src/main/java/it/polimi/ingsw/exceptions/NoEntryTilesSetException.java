package it.polimi.ingsw.exceptions;

public class NoEntryTilesSetException extends Exception {
    private String message;

    public NoEntryTilesSetException (String message){
        this.message=message;
        System.out.println(message);
    }
}
