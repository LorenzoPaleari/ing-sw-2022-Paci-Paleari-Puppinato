package it.polimi.ingsw.exceptions;

public class NotYourTurnException extends Exception{
    private final String message = "Not your Turn";

    @Override
    public String getMessage() {
        return message;
    }
}
