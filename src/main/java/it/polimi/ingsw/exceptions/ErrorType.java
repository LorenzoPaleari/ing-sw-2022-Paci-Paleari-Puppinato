package it.polimi.ingsw.exceptions;

public enum ErrorType {
    ALREADY_CHOSEN_CLOUD(1, "This cloud has already been chosen, please select another"),
    ALREADY_USED_CHARACTER(2, "A character has already been used this turn"),
    GENERAL_SUPPLY_FINISHED(3, "There are no more coins, please try another action"),
    MAX_STUDENT_REACHED(4, "Max student reached for the color: "),
    NO_ENTRY_TILES_SET(5, "You don't have any other No Entry Tile left or the chosen island has already one No Entry Tile on"),
    NOT_ENOUGH_MONEY(6, "You don't have enough coins (price = "),
    NOT_YOUR_TURN(7, "Not your Turn, please wait"),
    SAME_ASSISTANT(8, "Choose a different Assistant from others player"),
    TOO_MUCH_MOVES(9, "You can't go so far with your Assistant, please choose another Island"),
    WRONG_ACTION(10, "You have already moved all the student, please move Mother Nature"),
    WRONG_ACTION2(11, "Before moving mother nature, please move all the students"),
    WRONG_PHASE(12, "You must choose an Assistant"),
    WRONG_PHASE2(13, "You must move students or mother nature"),
    WRONG_PHASE3(14, "You have to choose a cloud");

    private final int number;
    private final String errorText;

    ErrorType (int number, String errorText){
        this.number = number;
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }
}
