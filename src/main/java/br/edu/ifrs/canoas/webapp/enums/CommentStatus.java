package br.edu.ifrs.canoas.webapp.enums;

public enum CommentStatus {
    ACTIVE("comment.status.active"),
    WAITING_REVIEW("comment.status.waiting_review"),
    UNDER_REVIEW("comment.status.under_review"),
    DELETED("comment.status.deleted");

    private String name;

    CommentStatus(String name) {
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
