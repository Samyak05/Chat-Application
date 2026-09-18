import java.io.*;
import java.net.*;

public class Server {

    ServerSocket server;
    Socket socket;

    BufferedReader br; //br object for input stream to read data sent by client
    PrintWriter out; // out object for output stream to write data into the client


    //Constructor
    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is Ready to accept connection!");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading(); // we may have the need to perform these both tasks simultaneously
            startWriting(); // so we will use multithreading here

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startReading() {
        // thread1 - data read krke deta rahega
        Runnable r1=()->{
            System.out.println("reader started.");
            while(true){
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        break;
                    }
                    System.out.println("Client: " + msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        // Now start thread r1
        new Thread(r1).start();
    }

    private void startWriting() {
        // thread2 - data user lega and send karega client tak
        Runnable r2=()->{
            System.out.println("writer started.");
            while (true){
                try {
                    // read the content from console, that data which is sent by client
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine(); // store in 'content' variable

                    out.println(content); //send that data to client
                    out.flush();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        };

        // Now start thread r2
        new Thread(r2).start();
    }


    static void main() {
        System.out.print("Starting the Server...");
        new Server();
    }
}
