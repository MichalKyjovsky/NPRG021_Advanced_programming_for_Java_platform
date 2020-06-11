package cz.cuni.mff.java.jmy.example;

import cz.cuni.mff.java.jmy.JMY;

public class Client {

    public static void main(String[] args) {
        MBean petr = JMY.connect(MBean.class, "Petr", "localhost", 8080);
        MBean karel = JMY.connect(MBean.class, "Karel", "localhost", 8080);
        
        System.out.println(petr.getName());
        System.out.println(petr.getID());
        petr.setID(1);
        System.out.println(petr.getID());
        
        System.out.println(karel.getName());
        System.out.println(karel.getID());
        
        MBean noobject = JMY.connect(MBean.class, "noobject", "localhost", 8080);
        System.out.println(noobject);
    }
    
}
