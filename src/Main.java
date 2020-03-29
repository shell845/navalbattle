import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author YC 03/29/2020
 */

public class Main {
    public static void main(String[] args) {
        NavalFrame f = new NavalFrame("Naval Battle");
        f.setVisible(true);

        while (true) {
            try {
                //TimeUnit.MICROSECONDS.sleep(30);
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            f.repaint();
        }
    }
}
