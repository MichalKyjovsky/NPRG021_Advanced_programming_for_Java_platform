package cz.cuni.mff.java.sysint;

import java.awt.*;

public class DesktopIntegrationTest {

    public static void main(String[] argv) throws Exception {
        if (Desktop.isDesktopSupported()) {
            System.out.println("Desktop is supported");
            Desktop dt = Desktop.getDesktop();
            for (Desktop.Action action: Desktop.Action.values()) {
                System.out.println(action.name() + " is " + (dt.isSupported(action) ? "" : "not ") + "supported");
            }

            dt.browse(new java.net.URI("http://www.cuni.cz"));
        } else {
            System.out.println("Desktop integration is not supported");
        }
    }
}
