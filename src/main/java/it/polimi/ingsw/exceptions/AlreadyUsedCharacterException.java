package it.polimi.ingsw.exceptions;

public class AlreadyUsedCharacterException extends Exception{
    private String message = "E' già stato usato un personaggio in questo turno";

    @Override
    public String getMessage() {
        return message;
    }
}
