package ru.netology;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static java.lang.Integer.parseInt;

public class Server {
    public final String serverName = "Администратор";

    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {

            Runnable sendMessage = () -> {
                String sendMessg;
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    sendMessg = scanner.nextLine();
                    sendMessageToAllClients(serverName + ": " + sendMessg);
                    Logger.writeToLog(serverName + ": " + sendMessg);
                }
            };


            serverSocket = new ServerSocket(readSettingsPort("settings.txt"));
            System.out.println("Сервер запущен!");
            new Thread(sendMessage).start();
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();


            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }

    public void sendMessageToAllClients(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }

    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public int readSettingsPort(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int port = 0;
        String wanted = "port=";
        for (String ln : lines) {
            if (ln.contains(wanted))
                port = parseInt(ln.substring(wanted.length()));

        }
        return port;
    }


    public static class Logger {
        static java.util.logging.Logger LOGGER;

        public static void writeToLog(String message) {
            try {
                LOGGER.log(Level.INFO, message);

            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "что-то пошло не так", e);
            }


        }


        static {
            try (FileInputStream ins = new FileInputStream("C:/Users/79056/IdeaProjects/WebChatCourse/Server/src/main/java/ru/netology/log.config")) {
                LogManager.getLogManager().readConfiguration(ins);
                LOGGER = java.util.logging.Logger.getLogger(Main.class.getName());
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }


        }
    }
}
