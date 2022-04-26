package it.polimi.ingsw.exceptions;

public class GeneralSupplyFinishedException extends Exception{
    private String message = "There are no more coins, please try another action";

    @Override
    public String getMessage() {
        return message;
    }
}
