package ch.teachu.teachuapi.enums;

public enum Role {
    PARENT,
    STUDENT,
    TEACHER,
    ADMIN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
