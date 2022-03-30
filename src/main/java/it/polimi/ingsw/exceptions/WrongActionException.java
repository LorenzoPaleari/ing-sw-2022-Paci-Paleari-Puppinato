package it.polimi.ingsw.exceptions;

public class WrongActionException extends Exception{
    private String message;
    public WrongActionException(int num){
        if (num == 0)
            message =  "You have already moved all the student, please move Mother Nature";
        else
            message = "Before moving mother nature, please move all the students";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
