package br.edu.ifrs.canoas.webapp.enums;

public enum AnnounceStatus {
    ACTIVE("announce.status.active"),
    WAITING_REVIEW("announce.status.waiting_review"),
    UNDER_REVIEW("announce.status.under_review"),
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
