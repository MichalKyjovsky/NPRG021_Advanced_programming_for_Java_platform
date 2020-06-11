package cz.cuni.mff.java.formannotations;

public class TestData {
    @FormField(name = "User name", kind = FieldKind.TEXT)
    String name;

    @FormField(name = "User password", kind = FieldKind.PASSWORD)
    String password;

    boolean active;

    public TestData() {
    }

    public TestData(String name, String password, boolean active) {
        this.name = name;
        this.password = password;
        this.active = active;
    }

    @Override
    public String toString() {
        return "TestData{" + "name=" + name + ", password=" + password + ", active=" + active + '}';
    }
}
