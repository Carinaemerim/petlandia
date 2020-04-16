package br.edu.ifrs.canoas.webapp.enums;

public enum AnimalAgeEnum {
    PUPPY("animal.age.puppy"),
    ADULT("animal.age.adult"),
    AGED("animal.age.aged");

    private String name;

    AnimalAgeEnum(String name) {
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
