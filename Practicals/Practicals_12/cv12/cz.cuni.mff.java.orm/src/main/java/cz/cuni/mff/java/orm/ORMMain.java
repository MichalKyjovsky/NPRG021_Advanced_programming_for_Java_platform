package cz.cuni.mff.java.orm;

public class ORMMain {

    public static void main(String[] args) throws Exception {
        ORM orm = new ORM("jdbc:h2:~/H2DBTestORM", "sa", "");
        orm.initForClasses(Data1.class, Data2.class);

        Data1 d1 = new Data1("Petr");
        Data2 d2 = new Data2("Karel");

        orm.save(d1, d2);

        orm.close();
    }
}
