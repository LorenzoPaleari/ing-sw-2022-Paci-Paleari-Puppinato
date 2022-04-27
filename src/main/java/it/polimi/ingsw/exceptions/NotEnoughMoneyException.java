package it.polimi.ingsw.exceptions;

public class NotEnoughMoneyException extends Exception{
    private String message = "You don't have enough coins";

    @Override
    public String getMessage() {
        return message;
    }
}
