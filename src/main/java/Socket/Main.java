package Socket;

import com.sun.corba.se.spi.activation.Server;
import com.sun.scenario.effect.impl.prism.PrRenderInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static Map<String, ClientSocket> clients = new HashMap<>();
    private ServerSocket server;
    private Socket client;
    public Main() {
        new Thread(() -> {
            try {
                server = new ServerSocket(8050);
                System.out.println("Server start");
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true){
                try {
                    client = server.accept();
                    ClientSocket socket = new ClientSocket(client);
//                        clients.add(socket);
//                        System.out.println("client connected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // client = server.isClosed()
                // погуглить как закрывать порт Closable + try with e resources , block finally? xnj, .получать сообщение, если юзера не существует
                // потренироваться все с лябмдами
            }
        }).start();
    }

    public static void main(String[] args) {
        new Main();
        System.out.println("Continue.....");
    }

}