package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Client2 {
    String localhost = "127.0.0.1";
    String text = "";
    public Client2() {

        try (Socket clientSocket = new Socket(localhost, readSettingsPort("settings.txt"));
             PrintWriter out = new
                     PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new
                     InputStreamReader(clientSocket.getInputStream()))) {
            Scanner scanner = new Scanner(System.in);
            Scanner inMessage = new Scanner(clientSocket.getInputStream());
            System.out.println("Введите имя пользователя:");
            out.println(scanner.nextLine());

            Runnable getMessage = () -> {
                while (true) {
                    if (inMessage.hasNext()) {
                        System.out.println(inMessage.nextLine());
                    }
                }

            };


            Thread thread1 = new Thread(getMessage);

            thread1.start();

            while (true) {
                text = scanner.nextLine();
                out.println(text);
                if (text.equals("/exit")) {

                    break;
                }

            }
            String resp = in.readLine();
            System.out.println(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int readSettingsPort(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int port = 0;
        String wanted = "port=";
        for (String ln : lines) {
            if (ln.contains(wanted))
                port = parseInt(ln.substring(wanted.length()));

        }
        return port;
    }
}
