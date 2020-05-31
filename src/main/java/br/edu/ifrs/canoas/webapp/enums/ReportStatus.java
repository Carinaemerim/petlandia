package br.edu.ifrs.canoas.webapp.enums;

public enum ReportStatus {
    WAITING_REVIEW("report.status.waiting_review"),
    REJECTED("report.status.rejected"),
    ACCEPTED("report.status.accepted");

    private String name;

    ReportStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName(){
        return this.name;
    }
}
