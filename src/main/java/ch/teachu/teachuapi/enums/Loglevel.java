package ch.teachu.teachuapi.enums;

public enum Loglevel {
    ERROR,
    WARN,
    INFO;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
