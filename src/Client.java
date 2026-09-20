import java.io.*;
import java.net.*;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //Constructor
    Client(){
        try {
            System.out.println("Sending request to Server...");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection established.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startReading() {
        Runnable r1 = ()->{
            System.out.println("Reader started.");

            try {
                while (!socket.isClosed()) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.print("Server terminated the connection");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: " + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(r1).start();
    }

    private void startWriting() {
        Runnable r2 = ()->{
            System.out.println("writer started");

            try {
                while (true) {
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br2.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }


    static void main() {
        System.out.println("This is Client...");
        new Client();
    }
}
