package ch.teachu.teachuapi.enums;

public enum ChatState {
    UNREAD,
    READ;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
