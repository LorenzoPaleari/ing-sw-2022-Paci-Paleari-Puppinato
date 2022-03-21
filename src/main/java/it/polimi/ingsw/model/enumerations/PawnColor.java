package it.polimi.ingsw.model.enumerations;

public enum PawnColor {
    GREEN("GREEN"), RED("RED"), YELLOW("YELLOW"), PINK("PINK"), BLUE("BLUE");

    private final String text;

    PawnColor(String text) {
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