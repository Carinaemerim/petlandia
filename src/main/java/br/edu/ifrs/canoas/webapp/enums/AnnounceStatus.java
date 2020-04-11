package br.edu.ifrs.canoas.webapp.enums;

public enum AnnounceStatus {
    ACTIVE("ACTIVE"),
    CREATED("CREATED"),
    INACTIVE("INACTIVE");

    private String name;

    AnnounceStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
