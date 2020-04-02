import com.shell.navalbattle.utils.PropertyMgr;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author YC 04/01/2020
 */

public class ConfigTest {
    @Test
    public void testConfig() {
        Properties props = new Properties();
        try {
            props.load(ConfigTest.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = (String) props.get("FRAMEHEIGHT");
        System.out.println(Integer.parseInt(s));
    }

    @Test
    public void testConfigKlass() {
        assertEquals("3", PropertyMgr.getConfig("initEnemyNum"));
    }
}
