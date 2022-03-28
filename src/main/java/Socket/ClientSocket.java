package Socket;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;


public class ClientSocket extends Thread{
    public Socket socket;
    public BufferedReader in;
    public PrintWriter out;
    String name;
    String mail;

    public ClientSocket(Socket x){
        this.socket = x;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run(){
        out.write("Ваше имя? \n");
        out.flush();
        try {
            name = in.readLine();
            mail = "@" + name;
            Main.clients.put(mail, this);
            System.out.println(name + " подключился");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        while (true) {
//            try {
//                String serverWord = in.readLine();
//                System.out.println(serverWord);
//                for (ClientSocket i: Main.clients) {
//                    i.out.write(name + ": " + serverWord + "\n");
//                    i.out.flush();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        while (true) {
            try {
                String serverWord = in.readLine();
                if (serverWord.equals("null")){
                    Main.clients.remove(this);
                    System.out.println(name + " disconnected");
                    break;
                }
                Boolean stop = false;
                String[] letter = serverWord.split(" ");
                String[] word = Arrays.copyOfRange(letter, 1, letter.length);
                for (Map.Entry<String, ClientSocket> i : Main.clients.entrySet()){
                    if (letter[0].equals(i.getKey())){
                        //String[] word = Arrays.copyOfRange(serverWord.split(" "), 1, serverWord.split(" ").length);

                        i.getValue().out.write( name + ": " + String.join(" " ,word) + "\n");
                        i.getValue().out.flush();
                        stop = true;
                        break;
                    }
                }
                if (stop){
                    continue;
                }


               // System.out.println(name + ": "+ serverWord);
                if (serverWord.equals("count")) {
                    // System.out.println(Main.clients.size());5
                    out.write(Main.clients.size() + "\n");
                    out.flush();
                    // serverword.split - massive, 0 equals , беру инпут стрим нудного клиента с . Лучше мапу с скиентом и именем
                } else {
                    System.out.println(name + ": "+ serverWord);
                    for (ClientSocket i : Main.clients.values()) {
                        if (!i.equals(this)) {
                            i.out.write(name + ": " + serverWord + "\n");
                            i.out.flush();
                        }
                    }

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }



//            Map<String, Client>
//            Client directMessage = map.get(userName);
//            if directMessage != null

        }
    }
}
