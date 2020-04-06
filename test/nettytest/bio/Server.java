/**
 * @author YC 04/06/2020
 */

package nettytest.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8848);
        Socket s = ss.accept();
        // ss.bind(new InetSocketAddress("localhost", 8848));
        // boolean started = true;

        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String read = reader.readLine();
        System.out.println("from client " + read);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write("this is server");
        bw.newLine();
        bw.flush();

        Scanner scanner = new Scanner(System.in);
        String str = null;

        while (str == null || !str.equals("bye") || !read.equals("bye")) {
            read = reader.readLine();
            System.out.println("from client " + read);

            str = scanner.nextLine();
            bw.write(str);
            bw.newLine();
            bw.flush();
            System.out.println(str);
        }

        /* while (started) {
            Socket s = ss.accept();
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String str = reader.readLine();
                    System.out.println(str);
                    reader.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } */
        bw.close();
        ss.close();
    }
}
