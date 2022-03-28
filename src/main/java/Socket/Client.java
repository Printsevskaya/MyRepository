package Socket;


import java.io.*;
import java.net.Socket;

public class Client {
    Socket client;
    BufferedReader in;
    PrintWriter out;
    BufferedReader  reader;
    String name;
    public Client() {
        while (true) {
           // lovecSoobsheniy();
            while (true) {
                try {
                    client = new Socket("localhost", 8050);
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out = new PrintWriter(client.getOutputStream(), true);
                    reader = new BufferedReader(new InputStreamReader(System.in));

                    System.out.println(in.readLine()); // ВАше имя?
                    name = reader.readLine(); // вписываем имя
                    break;
                } catch (IOException e) {
                    System.out.println("Сервер не доступен");
                    ;
                }
            }
            lovecSoobsheniy();
        }
    }

    public void lovecSoobsheniy(){
        out.write(name + "\n"); // отправляем имя
        out.flush();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String serverWord = in.readLine();
                        System.out.println(serverWord);
                    } catch (IOException e) {
                        break;
                    }
                }
            }
        }).start();

        System.out.println("Вы что-то хотели сказать? Введите это здесь:");
        while (true) {
            try {
                String word  = reader.readLine();
                out.write(word + "\n");
                out.flush();
            } catch (IOException e) {
                break;
            }
        }
//        System.out.println("Сервер лег");
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }

}
