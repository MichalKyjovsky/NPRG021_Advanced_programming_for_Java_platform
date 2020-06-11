package cz.cuni.mff.java.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public interface JNAApiInterface extends Library {
    JNAApiInterface INSTANCE = (JNAApiInterface) Native.load((Platform.isWindows() ? "msvcrt" : "c"), JNAApiInterface.class);

    void printf(String format, Object... args);
}
