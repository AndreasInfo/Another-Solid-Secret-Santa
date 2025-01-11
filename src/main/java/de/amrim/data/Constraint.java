package de.amrim.data;

public class Constraint {
    Type type;
    String nameA;
    String nameB;

    public Constraint(Type type, String nameA, String nameB){
        this.type = type;
        this.nameA = nameA;
        this.nameB = nameB;
    }

    public enum Type {
        IS_SANTA_FOR,
        IS_NO_SANTA_FOR;
    }

    public Type type() {
        return type;
    }

    public String nameA() {
        return nameA;
    }


    public String nameB() {
        return nameB;
    }

    @Override
    public String toString() {
        return "Constraint{" + nameA + " " + type + " " + nameB + "}";
    }
}
