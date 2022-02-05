package Socket;

import com.sun.corba.se.spi.activation.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<ClientSocket>  clients = new ArrayList<>();;
    public static class MyThread extends Thread {
//        static List<ClientSocket> clients;
        ServerSocket server;
        @Override
        public void run() {
           // clients = new ArrayList<>();
            server = null;
            try {
                server = new ServerSocket(8080);
                System.out.println("Server start");
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true){
                Socket client = null;
                try {
                    client = server.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ClientSocket user = new ClientSocket(client);
                user.start();
                clients.add(user);
                System.out.println("client connected");
//                try {
//                    assert client != null;
//                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(user.socket.getOutputStream()));
//                    out.write("You are connected!");
//                    out.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Thread thread = new MyThread();
        thread.start();
        System.out.println("Continue.....");
    }
}
