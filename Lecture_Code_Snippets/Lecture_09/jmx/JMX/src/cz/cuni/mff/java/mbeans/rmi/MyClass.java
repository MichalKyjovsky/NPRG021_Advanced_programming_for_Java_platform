package cz.cuni.mff.java.mbeans.rmi;

public class MyClass implements MyClassMBean {

    private int state = 0;
    private String hidden = null;

    @Override
    public int getState() {
        return (state);
    }

    @Override
    public void setState(int s) {
        state = s;
    }

    public String getHidden() {
        return (hidden);
    }

    public void setHidden(String h) {
        hidden = h;
    }

    @Override
    public void reset() {
        state = 0;
        hidden = null;
    }
}
