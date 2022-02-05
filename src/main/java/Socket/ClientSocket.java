package Socket;

import java.io.*;
import java.net.Socket;

public class ClientSocket extends Thread {

    Socket socket;
    public ClientSocket(Socket x){
        this.socket = x;
    }

    private static BufferedReader in;
    private static BufferedWriter out;

    @Override
    public void run() {
//        try {
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String message = in.readLine();
//            System.out.println(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            assert socket != null;
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            out.write("You are connected!");
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.write("You are connected" + "\n");
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                while (true) {
//
//                }
                // сервер должен рассылать сообщения всем
                // идентифицировать юзеров айди-имя
                // запрашивать имя клиента ???
                // join run start методы Thread
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String serverWord = in.readLine();
                        System.out.println(serverWord);
                        for (ClientSocket i: Main.clients) {
                            i.send(serverWord);
                            int x = 9;
                            x++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                while (true){
//                    try {
//                        String serverWord = in.readLine();
//                        for (ClientSocket i: Main.clients) {
//                            i.send(serverWord);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

    void send(String serverWord) throws IOException {
        out.write(serverWord.trim());
        out.flush();
    };

}
