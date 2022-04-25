package it.polimi.ingsw.model.enumerations;

public enum TowerColor {
    WHITE("WHITE"), BLACK("BLACK"), GREY("GREY");

    private final String text;

    /**
     * Default constructor.
     *
     * @param text the string representation of the tower color.
     */
    TowerColor(String text) {
        this.text = text;
    }

    /**
     * Returns the text of the player state.
     *
     * @return a String containing the text of the tower color.
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static TowerColor getColor(String color){
        for (TowerColor t : TowerColor.values())
            if(t.toString().equals(color))
                return t;
        return null;
    }
}