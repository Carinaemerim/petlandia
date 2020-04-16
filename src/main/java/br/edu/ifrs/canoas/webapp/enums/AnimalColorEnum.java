package br.edu.ifrs.canoas.webapp.enums;

public enum AnimalColorEnum {
    WHITE("animal.color.white"),
    BLACK("animal.color.black"),
    BROWN("animal.color.brown"),
    CARAMEL("animal.color.caramel"),
    GREY("animal.color.grey"),
    CREAM("animal.color.cream");

    private String name;

    AnimalColorEnum(String name) {
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
