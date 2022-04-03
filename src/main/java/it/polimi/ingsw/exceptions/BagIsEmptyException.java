package it.polimi.ingsw.exceptions;

public class BagIsEmptyException extends Exception {
    @Override
    public String getMessage() {
        return "Bag is Empty";
    }
}
