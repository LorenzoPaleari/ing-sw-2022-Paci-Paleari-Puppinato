package it.polimi.ingsw.exceptions;

import java.io.Serializable;

/**
 * Error type enum
 */
public enum ErrorType implements Serializable {
    ALREADY_CHOSEN_CLOUD("This cloud has already been chosen, please select another"),
    ALREADY_USED_CHARACTER("A character has already been used this turn"),
    MAX_STUDENT_REACHED("Max student reached for the color: "),
    NO_ENTRY_TILES_SET("You don't have any other No Entry Tile left or the chosen island has already one No Entry Tile on"),
    NOT_ENOUGH_MONEY( "You don't have enough coins (price = "),
    NOT_YOUR_TURN("Not your Turn, please wait"),
    SAME_ASSISTANT("Choose a different Assistant from others player"),
    TOO_MUCH_MOVES("You can't go so far with your Assistant, please choose another Island"),
    WRONG_ACTION("You have already moved all the student, please move Mother Nature"),
    WRONG_ACTION2("Before moving mother nature, please move all the students"),
    WRONG_PHASE("You must choose an Assistant"),
    WRONG_PHASE2("You must move students or mother nature"),
    WRONG_PHASE3("You have to choose a cloud");
    private final String errorText;

    /**
     * Constructor
     * @param errorText the error text
     */
    ErrorType (String errorText){
        this.errorText = errorText;
    }

    /**
     * gets error text
     * @return the error text
     */
    public String getErrorText() {
        return errorText;
    }
}
