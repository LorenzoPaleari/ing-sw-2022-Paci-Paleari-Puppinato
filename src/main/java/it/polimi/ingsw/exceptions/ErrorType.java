package it.polimi.ingsw.exceptions;

public enum ErrorType {
    ALREADY_CHOSEN_CLOUD(1, "This cloud has already been chosen, please select another");
    
    private final int number;
    private final String errorText;

    ErrorType (int number, String errorText){
        this.number = number;
        this.errorText = errorText;
    }
}
