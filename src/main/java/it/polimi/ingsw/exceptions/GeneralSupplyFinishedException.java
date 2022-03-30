package it.polimi.ingsw.exceptions;

public class GeneralSupplyFinishedException extends Exception{
    private String message = "You can't go so far with your Assistant, please choose another Island";

    @Override
    public String getMessage() {
        return message;
    }
}
