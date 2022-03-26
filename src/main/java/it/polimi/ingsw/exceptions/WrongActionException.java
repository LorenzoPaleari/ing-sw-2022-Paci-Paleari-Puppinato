package it.polimi.ingsw.exceptions;

public class WrongActionException extends Exception{
    public WrongActionException(int num){
        if (num == 0)
            System.out.println("You have already moved all the student, please move Mother Nature");
        else
            System.out.println("Before moving mother nature, please move all the students");
    }
}
