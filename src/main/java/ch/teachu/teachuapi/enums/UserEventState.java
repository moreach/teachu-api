package ch.teachu.teachuapi.enums;

public enum UserEventState {
    PENDING, // for user made absences
    EXCUSED,
    UNEXCUSED,
    HOLIDAY;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
