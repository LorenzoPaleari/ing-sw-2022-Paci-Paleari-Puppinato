package it.polimi.ingsw.enumerations;

public enum PlayerState {
    PLANNING("PLANNING"), ACTION("ACTION"), WAIT("WAIT");

    private final String text;

    /**
     * Default constructor.
     *
     * @param text the string representation of the player state.
     */
    PlayerState(String text) {
        this.text = text;
    }

    /**
     * Returns the text of the player state.
     *
     * @return a String containing the text of the player state.
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}