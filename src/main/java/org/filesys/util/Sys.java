package org.filesys.util;

import java.io.File;
import java.util.logging.Logger;

public class Sys {
    public static Boolean isRestricted = null;

    public static void err(Exception ex) {
        Sys.println(ex.getMessage());
        for (StackTraceElement element : ex.getStackTrace()) {
            Sys.println(element.toString());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void println(String s) {
        if (Sys.isRestricted == null) {
            try {
                new File("/test").exists();
                Sys.isRestricted = false;
            } catch (Exception ex) {
                Sys.isRestricted = true;
            }
        }
        if (Sys.isRestricted) {
            Logger.getAnonymousLogger().info(s);
        } else {
            java.lang.System.out.println(s);
        }
    }
}
