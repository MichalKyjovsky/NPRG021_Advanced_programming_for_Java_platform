package cz.cuni.mff.ms.kyjovsm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
	    // TODO: 1. Vypis vsecnhy fieldy a metody nejake tridy (Napriklad Integer)
//        try {
//            Class cls = Class.forName("java.lang.Integer");
//            Field[] fld = cls.getFields();
//            Method[] mtd = cls.getMethods();
//
//            for (Field item : fld){
//                System.out.println(item);
//            }
//
//            for (Method item : mtd){
//                System.out.println(item);
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        // TODO: 2. Zavolat metodu s modifikatorem pristupu private
        try{
            Class cls = Class.forName("cz.cuni.mff.ms.kyjovsm.ReflectionAPIPractice");
            ReflectionAPIPractice rf = new ReflectionAPIPractice();
            Method privateMethod = cls.getDeclaredMethod("sayHello");
            Method privateStaticMethod = cls.getDeclaredMethod("sayStaticHello");
            privateMethod.setAccessible(true);
            privateStaticMethod.setAccessible(true);
            privateMethod.invoke(rf);
            privateStaticMethod.invoke(null);
        }catch (Exception e){
            e.printStackTrace();
        }

        // TODO: 3. https://github.com/ahornace/TableCreator
    }
}
