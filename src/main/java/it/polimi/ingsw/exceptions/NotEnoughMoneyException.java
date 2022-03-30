package it.polimi.ingsw.exceptions;

public class NotEnoughMoneyException extends Exception{
    private String message;
    public NotEnoughMoneyException(String s) {
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
