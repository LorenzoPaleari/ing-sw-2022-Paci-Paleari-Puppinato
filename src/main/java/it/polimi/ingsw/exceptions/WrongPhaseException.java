package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.model.enumerations.PlayerState;

public class WrongPhaseException extends Exception{
    private String  message;

    @Override
    public String getMessage() {
        return message;
    }

    public WrongPhaseException(PlayerState state) {
        if (state.equals(PlayerState.PLANNING))
            message = "You must choose an Assistant";
        else if (state.equals(PlayerState.ACTION))
            message = "You must move students or mother nature";
        else
            message = "You have to choose a cloud";
    }
}
