package it.polimi.ingsw.exceptions;

public class AlreadyUsedCharacterException extends Exception{
    private String message = "A character has already been used this turn";

    @Override
    public String getMessage() {
        return message;
    }
}
