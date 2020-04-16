package br.edu.ifrs.canoas.webapp.enums;

public enum AnimalGenderEnum {
    MALE("animal.gender.male"),
    FEMALE("animal.gender.female");

    private String name;

    AnimalGenderEnum(String name) {
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
