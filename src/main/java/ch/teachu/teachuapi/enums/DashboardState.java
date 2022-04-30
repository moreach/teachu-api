package ch.teachu.teachuapi.enums;

public enum DashboardState {
    PRIVATE,
    PUBLIC;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
