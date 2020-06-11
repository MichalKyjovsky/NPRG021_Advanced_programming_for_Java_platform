package cz.cuni.mff.java.orm;

public class Data2 {
    @Key
    private int id;
    private String value;

    public Data2(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
