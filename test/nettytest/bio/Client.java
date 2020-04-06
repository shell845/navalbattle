/**
 * @author YC 04/06/2020
 */

package nettytest.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 8848);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write("this is client");
        bw.newLine();
        bw.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String read = reader.readLine();
        System.out.println("from server " + read);

        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        while (!str.equals("bye") && !read.equals("bye")) {
            bw.write(str);
            bw.newLine();
            bw.flush();
            System.out.println(str);

            read = reader.readLine();
            System.out.println("from server " + read);

            str = scanner.nextLine();
        }

        bw.close();
        s.close();
    }
}
