package ru.netology;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class ClientHandler implements Runnable {
    private Server server;
    private String clientName;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private static final String HOST = "localhost";
    private static final int PORT = 8081;
    private Socket clientSocket = null;
    private static int clients_count = 0;

    public ClientHandler(Socket socket, Server server) {
        try {
            clients_count++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            if (inMessage.hasNext()) {
                clientName = inMessage.nextLine();
            }
            while (true) {
                server.sendMessageToAllClients("Новый участник "+clientName+ " вошёл в чат!");
                server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
                Logger.writeToLog("Новый участник "+clientName+ " вошёл в чат!");
                Logger.writeToLog("Клиентов в чате = " + clients_count);
                break;
            }

            while (true) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.equalsIgnoreCase("/exit")) {
                        server.sendMessageToAllClients("Участник "+clientName+"  вышёл из чата!");
                        Logger.writeToLog("Участник "+clientName+"  вышёл из чата!");
                        clients_count--;
                        server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
                        Logger.writeToLog("Клиентов в чате = " + clients_count);
                        break;
                    }
                    Logger.writeToLog(clientName+": "+clientMessage);
                    System.out.println(clientName+": "+clientMessage);
                    server.sendMessageToAllClients(clientName+": "+clientMessage);
                }
                Thread.sleep(100);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            this.close();
        }
    }
    // отправляем сообщение
    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        server.removeClient(this);
        clients_count--;
        server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
    }
}

