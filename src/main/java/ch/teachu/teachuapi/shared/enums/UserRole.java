package ch.teachu.teachuapi.shared.enums;

public enum UserRole {
    parent(0),
    student(1),
    teacher(2),
    admin(3);

    private final int level;

    UserRole(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
