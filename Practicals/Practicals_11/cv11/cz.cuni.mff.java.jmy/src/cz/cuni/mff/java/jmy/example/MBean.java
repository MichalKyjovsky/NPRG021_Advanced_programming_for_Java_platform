package cz.cuni.mff.java.jmy.example;

import cz.cuni.mff.java.jmy.Mgmt;

@Mgmt
public interface MBean {
    String getName();
    int getID();
    void setID(int value);
}
