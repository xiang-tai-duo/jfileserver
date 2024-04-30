package org.filesys.util;

import java.io.File;

public class SysFiles {
    public static boolean checkExists(File file) {
        boolean b = false;
        try {
            b = file.exists();
        } catch (SecurityException ignored) {

        }
        Sys.println(String.format("Jvm.checkExists(): %s is %s", file.getAbsolutePath(), b));
        return b;
    }
}
