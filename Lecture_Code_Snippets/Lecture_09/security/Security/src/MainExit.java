
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainExit {

    public static void main(String[] argv) {
        if (argv.length > 0) {
            System.setSecurityManager(new ExitSecurityManager());
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("call Bad Exit? [y/n]: ");
            if (br.readLine().equals("y")) {
                new BadExit().exit();
            }
        } catch (IOException ex) {
            Logger.getLogger(MainExit.class.getName()).log(Level.SEVERE, "IOException", ex);
        }
        System.exit(0);
    }

    private static class ExitSecurityManager extends SecurityManager {
        @Override
        public void checkExit(int status) throws SecurityException {
            super.checkExit(status);
            Class<?>[] context = getClassContext();
            for (Class<?> clazz : context) {
                String name = clazz.getName();
                if (!name.startsWith("java.") && !name.startsWith("MainExit")) {
                    System.out.println("No exit allowed from " + name);
                    throw new SecurityException();
                }
            }
        }
    }
}
