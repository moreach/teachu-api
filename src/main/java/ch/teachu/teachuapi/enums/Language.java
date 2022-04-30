package ch.teachu.teachuapi.enums;

public enum Language {
    GERMAN,
    FRENCH,
    ENGLISH;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
