package br.edu.ifrs.canoas.webapp.enums;

public enum Role {
    ROLE_USER("user.role.user"),
    ROLE_MODERATOR("user.role.moderator"),
    ROLE_ADMIN("user.role.admin");

    private String name;

    Role(String name) {
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
