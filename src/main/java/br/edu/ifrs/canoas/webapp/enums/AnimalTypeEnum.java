package br.edu.ifrs.canoas.webapp.enums;

public enum AnimalTypeEnum {
    DOG("animal.type.dog"),
    CAT("animal.type.cat");

    private String name;

    AnimalTypeEnum(String name) {
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
