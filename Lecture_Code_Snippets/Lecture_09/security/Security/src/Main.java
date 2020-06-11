
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] argv) {
        System.out.println(System.getSecurityManager());
        //System.setSecurityManager(null);
        try (BufferedReader r = new BufferedReader(new FileReader("Security/Security.iml"))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "IOException", ex);
        }
    }

}
