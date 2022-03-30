package it.polimi.ingsw.exceptions;

public class AlreadyChosedCloudException extends Exception{
    private String message = "This cloud has already been chose, please select another" ;

    @Override
    public String getMessage() {
        return message;
    }
}
