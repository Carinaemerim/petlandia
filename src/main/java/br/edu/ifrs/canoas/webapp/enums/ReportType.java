package br.edu.ifrs.canoas.webapp.enums;

public enum ReportType {
    ANNOUNCE("report.type.announce"),
    COMMENT("report.type.comment"),
    USER("report.type.user");

    private final String name;

    ReportType(String name) {
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
