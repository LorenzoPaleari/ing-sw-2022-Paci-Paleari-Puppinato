package it.polimi.ingsw.model.enumerations;

public enum CharacterType {
    ADD_STUDENT_ISLAND(1, "ADD_STUDENT_ISLAND", "Take 1 student from this card and place it on an Island of your choice."),
    CONTROL_PROFESSOR(2,"CONTROL_PROFESSOR", "During this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them."),
    FAKE_MOTHER_NATURE(3,"FAKE_MOTHER_NATURE", "Choose an Island and resolve the Island as if Mother Nature had ended her movement there. Mother Nature will still move and the Island where she ends her movement will also be resolved."),
    ADD_MOVES(1, "ADD_MOVES", "You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played."),
    NO_ENTRY_TILES(2,"NO_ENTRY_TILES", "Place a No Entry tile on an Island of your choice. The first time Mother Nature ends her movement there, put the No Entry tile back onto this card DO NOT calculate influence on that Island, or place any Towers."),
    NO_TOWER(3, "NO_TOWER", "When resolving a Conquering on an Island, Towers do not count towards influence."),
    REPLACE_STUDENT(1,"REPLACE_STUDENT", "You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance."),
    MORE_INFLUENCE(2,"MORE_INFLUENCE", "During the influence calculation this turn, you count as, having 2 more influence."),
    NO_COLOR(3,"NO_COLOR", "Choose a color of Student: during the influence calculation this turn, that color adds no influence."),
    STUDENT_EXCHANGE(1, "STUDENT_EXCHANGE", "You may exchange up to 2 Students between your Entrance and your Dining Room."),
    ADD_STUDENT_DINING(2,"ADD_STUDENT_DINING", "Take 1 Student from this card and place it in your Dining Room."),
    RETURN_STUDENT(3, "RETURN_STUDENT", "Choose a type of Student; every player (including yourself) must return 3 Students of that type from their Dining Room to the bag. If any player has fewer than 3 Students of that type, return as many Students as they have.");

    private final int price;
    private final String name;
    private final String description;

    /**
     * Default constructor.
     *
     * @param name the string representation of the pawn color.
     */
    CharacterType(int cost, String name, String description) {
        this.name = name;
        this.price = cost;
        this.description = description;
    }

    /**
     * Returns the text of the player state.
     *
     * @return a String containing the text of the pawn color.
     */
    public String getText() {
        return name;
    }

    public String getDescription() {
        return description;
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
        return name;
    }
}
