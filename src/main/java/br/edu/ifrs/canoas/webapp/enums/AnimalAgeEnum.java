package br.edu.ifrs.canoas.webapp.enums;

public enum AnimalAgeEnum {
    PUPPY("animal.age.puppy"),
    TEENAGER("animal.age.teenager"),
    ADULT("animal.age.adult"),
    AGED("animal.age.aged"),
    GERIATRIC("animal.age.geriatric");

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
