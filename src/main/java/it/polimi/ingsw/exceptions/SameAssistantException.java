package it.polimi.ingsw.exceptions;

public class SameAssistantException extends Exception{
    private String message = "Choose a different Assistant from others player";

    @Override
    public String getMessage() {
        return message;
    }
}
