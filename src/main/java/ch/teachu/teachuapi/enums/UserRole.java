package ch.teachu.teachuapi.enums;

public enum UserRole {
    PARENT(0),
    STUDENT(1),
    TEACHER(2),
    ADMIN(3);

    private final int level;

    UserRole(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
