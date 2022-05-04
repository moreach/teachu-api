package ch.teachu.teachuapi.enums;

public enum Sex {
    MALE,
    FEMALE,
    OTHER;
    // TODO rename (others too)
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
