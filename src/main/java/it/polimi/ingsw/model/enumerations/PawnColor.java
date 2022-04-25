package it.polimi.ingsw.model.enumerations;

public enum PawnColor {
    GREEN(0, "GREEN"), RED(1, "RED"), YELLOW(2, "YELLOW"), PINK(3, "PINK"), BLUE(4, "BLUE");

    private final int index;
    private final String text;

    PawnColor(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public static PawnColor getColor(int i){
        for (PawnColor c : PawnColor.values())
            if(c.getIndex() == i)
                return c;
        return null;
    }
    public static PawnColor lookup(String color) {
        for (PawnColor p : PawnColor.values()) {
            if (p.toString().equalsIgnoreCase(color)) {
                return p;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}