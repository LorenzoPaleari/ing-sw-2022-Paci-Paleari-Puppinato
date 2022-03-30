package it.polimi.ingsw.exceptions;

public class SameAssistantException extends Exception{
    private String message = "Choose an Assistant different from others player";

    @Override
    public String getMessage() {
        return message;
    }
}
