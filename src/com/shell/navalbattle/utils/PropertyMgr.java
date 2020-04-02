/**
 * @author YC 04/01/2020
 */

package com.shell.navalbattle.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    private static Properties props;

    static {
        try {
            props = new Properties();
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConfig(String key) {
        return (String) props.get(key);
    }
}
