package com.shell.navalbattle;

import com.shell.navalbattle.netty.Client;
import com.shell.navalbattle.utils.Audio;

/**
 * @author YC 03/29/2020
 */

public class Main {
    public static void main(String[] args) {
        NavalFrame.MAIN_FRAME.setVisible(true);

        // new Thread(()->new Audio("audios/background.wav").loop()).start();

        // new Thread(() -> {
        while (true) {
            try {
                //TimeUnit.MICROSECONDS.sleep(30);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NavalFrame.MAIN_FRAME.repaint();
        }
        // }).start();

        // Client.client_instance.connect();
    }
}
