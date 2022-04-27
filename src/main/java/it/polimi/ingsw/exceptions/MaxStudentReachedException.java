package it.polimi.ingsw.exceptions;

public class MaxStudentReachedException extends Exception{
    private String message ="Max student reached in this island";

    @Override
    public String getMessage() {
        return message;
    }
}
