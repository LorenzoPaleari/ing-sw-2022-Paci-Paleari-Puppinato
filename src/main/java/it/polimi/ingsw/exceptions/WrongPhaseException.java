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
            message = "Devi scegliere un assistente";
        else if (state.equals(PlayerState.ACTION))
            message = "Devi spostare gli studenti o muovere madre natura";
        else
            message = "Devi scegliere una Nuvola";
    }
}
