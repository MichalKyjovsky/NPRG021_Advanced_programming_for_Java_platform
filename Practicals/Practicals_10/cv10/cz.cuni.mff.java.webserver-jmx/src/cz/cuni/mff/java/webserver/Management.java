package cz.cuni.mff.java.webserver;

import javax.management.MXBean;

@MXBean
public interface Management {
    void shutdown();
    String getDirectory();
    void setDirectory(String dir);
    String getState();
    void pause();
    void resume();
}
