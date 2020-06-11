package cz.cuni.mff.java.mbeans.rmi;

public interface MyClassMBean {
    int getState();
    void setState(int s);
    void reset();
}
