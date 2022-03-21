package it.polimi.ingsw.model.enumerations;

public enum PawnColor {
    GREEN(0, "GREEN"), RED(1, "RED"), YELLOW(2, "YELLOW"), PINK(3, "PINK"), BLUE(4, "BLUE");

    private final int index;
    private final String text;

    PawnColor(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}