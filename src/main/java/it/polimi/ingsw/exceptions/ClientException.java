package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.model.enumerations.PawnColor;

import java.io.Serializable;

public class ClientException extends Exception implements Serializable {

    private ErrorType errorType;
    private PawnColor pawnColor = null;
    private int price = -1;

    public ClientException(ErrorType errorType){
        this.errorType = errorType;
    }

    public ClientException(ErrorType errorType, PawnColor pawnColor){
        this.errorType = errorType;
        this.pawnColor = pawnColor;
    }

    public ClientException(ErrorType errorType, int price){
        this.price = price;
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public int getPrice() {
        return price;
    }

    public PawnColor getPawnColor() {
        return pawnColor;
    }
}
