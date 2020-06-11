package cz.cuni.mff.java.orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ORM {

    private final Connection con;
    private final Statement stm;

    public ORM(String url, String user, String passwd) throws SQLException {
        con = DriverManager.getConnection(url, user, passwd);
        stm = con.createStatement();
    }

    public void initForClasses(Class<?>... classes) throws SQLException {
        List<String> sqls = new ArrayList<>();
        for (Class<?> clazz : classes) {
            boolean hasId = false;
            StringBuilder sb = new StringBuilder("create table if not exists ");
            sb.append(clazz.getSimpleName()).append(" (");
            for (Field f: clazz.getDeclaredFields()) {
                sb.append(f.getName()).append(" ");
                if (f.getType() == int.class) {
                    sb.append("int");
                    if (f.isAnnotationPresent(Key.class)) {
                        sb.append(" identity");
                        hasId = true;
                    }
                } else if (f.getType() == String.class) {
                    sb.append("varchar");
                } else {
                    throw new RuntimeException("Unsupported field type");
                }
                sb.append(", ");
            }
            if (!hasId) {
                Logger.getLogger(ORM.class.getName()).log(Level.SEVERE, "Class " + clazz.getName() + " has no key");
                throw new RuntimeException("Class " + clazz.getName() + " has no key");
            }
            sb.delete(sb.lastIndexOf(","), sb.length());
            sb.append(")");
            sqls.add(sb.toString());
            Logger.getLogger(ORM.class.getName()).log(Level.INFO, sb::toString);
        }
        for (String create: sqls) {
            stm.executeUpdate(create);
        }
    }

    public void save(Object... objects) throws SQLException, IllegalAccessException {
        // Warning - we need a test whether objects already exist in DB
        List<String> sqls = new ArrayList<>();
        for (Object obj: objects) {
            Class<?> clazz = obj.getClass();
            StringBuilder sb = new StringBuilder("insert into ");
            sb.append(clazz.getSimpleName());
            sb.append(" (");
            for (Field f: clazz.getDeclaredFields()) {
                if (!f.isAnnotationPresent(Key.class)) {
                    sb.append(f.getName());
                    sb.append(", ");
                }
            }
            sb.delete(sb.lastIndexOf(","), sb.length());
            sb.append(") values (");
            for (Field f: clazz.getDeclaredFields()) {
                if (!f.isAnnotationPresent(Key.class)) {
                    f.setAccessible(true);
                    if (f.getType() == String.class) {
                        sb.append("'");
                    }
                    sb.append(f.get(obj).toString());  // WARNING - strings need to be preprocessed for '
                    if (f.getType() == String.class) {
                        sb.append("'");
                    }
                    sb.append(", ");
                }
            }
            sb.delete(sb.lastIndexOf(","), sb.length());
            sb.append(")");
            Logger.getLogger(ORM.class.getName()).log(Level.INFO, sb::toString);
            sqls.add(sb.toString());
            for (String create: sqls) {
                stm.executeUpdate(create);
            }
        }
    }

    public void close() throws SQLException {
        stm.close();
        con.close();
    }
}
