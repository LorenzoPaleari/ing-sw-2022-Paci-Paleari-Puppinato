package it.polimi.ingsw.exceptions;

public class AlreadyChosenCloudException extends Exception{

    @Override
    public String getMessage() {
        return "This cloud has already been chose, please select another";
    }
}
