package it.polimi.ingsw.model.enumerations;

public enum TowerColor {
    WHITE("WHITE", 0), BLACK("BLACK", 1), GREY("GREY", 2);

    private final String text;
    private final Integer index;

    /**
     * Default constructor.
     *
     * @param text the string representation of the tower color.
     */
    TowerColor(String text, Integer index) {
        this.text = text;
        this.index = index;
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

    public Integer getIndex(){
        return index;
    }

    public static TowerColor getColor(String color){
        for (TowerColor t : TowerColor.values())
            if(t.toString().equals(color.toUpperCase()))
                return t;
        return null;
    }

    public static TowerColor getColor(int color){
        for (TowerColor t : TowerColor.values())
            if(t.index.equals(color))
                return t;
        return null;
    }
}