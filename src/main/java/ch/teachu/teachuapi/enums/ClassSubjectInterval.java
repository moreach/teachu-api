package ch.teachu.teachuapi.enums;

public enum ClassSubjectInterval {
    WEEKLY,
    EVERY_OTHER,
    MONTHLY;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
