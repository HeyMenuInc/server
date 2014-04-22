package com.cloudstone.emenu;

import org.springframework.beans.BeansException;

import java.io.File;
import java.util.Map;

/**
 * Created by charliez on 4/21/14.
 */
public class AppConfig {

    public void setMaps(Map<Object, Object> maps) throws BeansException {
        if (AppConfig.maps == null) {
            AppConfig.maps = maps;
        }
    }

    private static Map<Object, Object> maps = null;

    public static Map config() {
        return AppConfig.maps;
    }

    public static String getDBFileName() {
        return (String) config().get("dbFileName");
    }

    public static String getCloudstoneDataDir() {
        return (String) config().get("cloudstoneDataDir");
    }

    public static String getDBFilePath() {
        return (new File(getCloudstoneDataDir(), getDBFileName())).getAbsolutePath();
    }
}
