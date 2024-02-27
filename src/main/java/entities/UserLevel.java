package entities;

public enum UserLevel {
    DEBUTANT("Debutant"),
    INTERMEDIAIRE("Intermediaire"),
    AVANCE("Avance"),
    NULL("Null");
    private final String displayName;

    UserLevel(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
