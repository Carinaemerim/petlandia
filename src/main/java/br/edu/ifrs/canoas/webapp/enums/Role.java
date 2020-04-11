package br.edu.ifrs.canoas.webapp.enums;

public enum Role {
    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
