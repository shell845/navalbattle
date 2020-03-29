import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author YC 03/29/2020
 */

public class Ship {
    private int xPos, yPos;
    public static final int SPEED = 5;
    private Directions dir;
    private boolean pressL, pressR, pressU, pressD;

    public Ship(int x, int y, Directions d) {
        this.xPos = x;
        this.yPos = y;
        this.dir = d;
    }


    public void paint(Graphics g) {
        g.fill3DRect(this.xPos, this.yPos, 30, 30, true);
        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                pressL = true;
                break;
            case KeyEvent.VK_UP:
                pressU = true;
                break;
            case KeyEvent.VK_RIGHT:
                pressR = true;
                break;
            case KeyEvent.VK_DOWN:
                pressD = true;
                break;
        }
        setDirection();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                pressL = false;
                break;
            case KeyEvent.VK_UP:
                pressU = false;
                break;
            case KeyEvent.VK_RIGHT:
                pressR = false;
                break;
            case KeyEvent.VK_DOWN:
                pressD = false;
                break;
        }
        setDirection();
    }

    private void setDirection() {
        if (!pressL && !pressR && !pressU && !pressD) dir = Directions.STOP;
        if (pressL && !pressR && !pressU && !pressD) dir = Directions.L;
        if (!pressL && pressR && !pressU && !pressD) dir = Directions.R;
        if (!pressL && !pressR && pressU && !pressD) dir = Directions.U;
        if (!pressL && !pressR && !pressU && pressD) dir = Directions.D;
    }

    private void move() {
        switch (this.dir) {
            case L:
                this.xPos -= SPEED;
                break;
            case R:
                this.xPos += SPEED;
                break;
            case U:
                this.yPos += SPEED;
                break;
            case D:
                this.yPos -= SPEED;
                break;
        }
    }


}
