package cn.qqhxj.fastcode.core.config;

import java.io.File;
import java.sql.Connection;

public class FastCodeHelper {
    private FastCodeConfig fastCodeConfig;

    public void setFastCodeConfig(FastCodeConfig fastCodeConfig) {
        this.fastCodeConfig = fastCodeConfig;
    }

    public String getConfigPath() {
        return getPath(fastCodeConfig.getConfigPath());
    }

    private String getPath(String p) {
        String classPathName = "classPath:";
        String path = null;
        if (p.startsWith(classPathName)) {
            path = this.getClass().getClassLoader().getResource(p.substring(classPathName.length())).getPath();
        } else {
            path = new File(p).getAbsolutePath();
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public String getTableDataPath() {
        return getPath(fastCodeConfig.getTableDataPath());
    }

}
