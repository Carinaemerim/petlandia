package br.edu.ifrs.canoas.webapp.enums;

public enum UserStatus {
    ACTIVE("user.status.active"),
    WAITING_REVIEW("user.status.waiting_review"),
    DELETED("user.status.deleted");

    private final String name;

    UserStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }
}
