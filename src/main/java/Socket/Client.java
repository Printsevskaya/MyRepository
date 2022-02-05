package Socket;

import java.io.*;
import java.net.Socket;

public class Client {
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 8080);
        reader = new BufferedReader(new InputStreamReader(System.in));
        // читать соообщения с сервера
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        // писать туда же
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

//        System.out.println("Вы что-то хотели сказать? Введите это здесь:");
//        // если соединение произошло и потоки успешно созданы - мы можем
//        //  работать дальше и предложить клиенту что то ввести
//        // если нет - вылетит исключение
//        String word = reader.readLine(); // ждём пока клиент что-нибудь
//        // не напишет в консоль
//        out.write(word + "\n"); // отправляем сообщение на сервер
//        out.flush();
//        String serverWord = in.readLine(); // ждём, что скажет сервер
//        System.out.println(serverWord); // получив - выводим на экран

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Вы что-то хотели сказать? Введите это здесь:");
                String word = null;
                while (true) {
                    try {
                        word = reader.readLine();
                        out.write(word + "\n");
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String serverWord = in.readLine();
                        System.out.println(serverWord);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}