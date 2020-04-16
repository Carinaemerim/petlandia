package br.edu.ifrs.canoas.webapp.enums;

public enum AnimalCastratedEnum {
    YES("animal.castrated.yes"),
    NO("animal.castrated.no"),
    UNKNOWN("animal.castrated.unknown");

    private String name;

    AnimalCastratedEnum(String name) {
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
