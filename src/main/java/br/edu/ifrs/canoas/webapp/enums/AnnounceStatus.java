package br.edu.ifrs.canoas.webapp.enums;

public enum AnnounceStatus {
    ACTIVE("announce.status.active"),
    CREATED("announce.status.created"),
    INACTIVE("announce.status.inactive");

    private String name;

    AnnounceStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
