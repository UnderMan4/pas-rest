package p.lodz.pl.pas.model_web;

public enum AccessLevel {

    UserAdministrator("UserAdministrator"),
    ResourceAdministrator("ResourceAdministrator"),
    User("User");

    private final String label;

    AccessLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
