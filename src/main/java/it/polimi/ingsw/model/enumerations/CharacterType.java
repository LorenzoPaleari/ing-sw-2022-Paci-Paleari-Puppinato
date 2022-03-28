package it.polimi.ingsw.model.enumerations;

public enum CharacterType {
    ADD_STUDENT_ISLAND(1, "ADD_STUDENT_ISLAND"),
    CONTROL_PROFESSOR(2,"CONTROL_PROFESSOR"),
    FAKE_MOTHER_NATURE(3,"FAKE_MOTHER_NATURE"),
    ADD_MOVES(1, "ADD_MOVES"),
    NO_ENTRY_TILES(2,"NO_ENTRY_TILES"),
    NO_TOWER(3, "NO_TOWER"),
    REPLACE_STUDENT(1,"REPLACE_STUDENT"),
    MORE_INFLUENCE(2,"MORE_INFLUENCE"),
    NO_COLOR(3,"NO_COLOR"),
    STUDENT_EXCHANGE(1, "STUDENT_EXCHANGE"),
    ADD_STUDENT_DINING(2,"ADD_STUDENT_DINING"),
    RETURN_STUDENT(3, "RETURN_STUDENT");

    private final int price;
    private final String text;

    /**
     * Default constructor.
     *
     * @param text the string representation of the pawn color.
     */
    CharacterType(int cost, String text) {
        this.text = text;
        this.price = cost;
    }

    /**
     * Returns the text of the player state.
     *
     * @return a String containing the text of the pawn color.
     */
    public String getText() {
        return text;
    }

    public int getPrice() {
        return price;
    }

    public int hasStudent(){
        int value = 0;

        if (this.equals(ADD_STUDENT_ISLAND) || this.equals(ADD_STUDENT_DINING))
            value = 4;

        if (this.equals(REPLACE_STUDENT))
            value = 6;

        return value;
    }

    @Override
    public String toString() {
        return text;
    }
}
