package it.polimi.ingsw.model.enumerations;

public enum PawnColor {
    GREEN("GREEN"), RED("RED"), YELLOW("YELLOW"), PINK("PINK"), BLUE("BLUE");

    private final String text;

    /**
     * Default constructor.
     *
     * @param text the string representation of the pawn color.
     */
    PawnColor(String text) {
        this.text = text;
    }

    /**
     * Returns the text of the player state.
     *
     * @return a String containing the text of the pawn color.
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}