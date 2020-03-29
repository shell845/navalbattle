import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author YC 03/29/2020
 */

public class NavalFrame extends Frame {
    private static final int FRAME_LOCATION_X = 0, FRAME_LOCATION_Y = 0;
    private static final int FRAME_WIDTH = 800, FRAME_HEIGHT = 600;

    Ship myShip;
    Ship enemyShip;

    public NavalFrame(String title) {
        this.setTitle(title);
        this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        this.addKeyListener(new NavalKeyListener());

        myShip = new Ship(50, 50, Directions.R);
        enemyShip = new Ship(100, 100, Directions.R);
    }

    @Override
    public void paint(Graphics g) {
        myShip.paint(g);
        enemyShip.paint(g);
    }

    private class NavalKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            myShip.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myShip.keyReleased(e);
        }
    }
}
