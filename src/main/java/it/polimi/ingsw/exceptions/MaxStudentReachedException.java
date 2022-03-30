package it.polimi.ingsw.exceptions;

public class MaxStudentReachedException extends Exception{
    private String message;
    public MaxStudentReachedException(String s) {
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
