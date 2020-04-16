package br.edu.ifrs.canoas.webapp.enums;

public enum AnimalSizeEnum {
    SMALL("animal.size.small"),
    MEDIUM("animal.size.medium"),
    LARGE("animal.size.large");

    private String name;

    AnimalSizeEnum(String name) {
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
