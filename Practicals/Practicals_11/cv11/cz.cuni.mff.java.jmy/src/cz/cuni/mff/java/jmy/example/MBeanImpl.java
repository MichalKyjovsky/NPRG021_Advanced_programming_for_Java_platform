package cz.cuni.mff.java.jmy.example;

public class MBeanImpl implements MBean {
    
    private String name;
    private int id;

    public MBeanImpl(String name, int id) {
        this.name = name;
        this.id = id;
    }    

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int value) {
        id = value;
    }
}
