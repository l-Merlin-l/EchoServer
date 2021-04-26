package ru.geekbrains.JavaCore2.chat2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private static MyServer server;

    public static MyServer getServer() {
        return server;
    }

    private final int PORT = 8080;
    private List<ClientHandler> clients;
    private AuthService authService;

    public MyServer() {
        server = this;
        try (ServerSocket server = new ServerSocket(PORT)){
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true){
                Socket socket = server.accept();
                new ClientHandler(socket);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public synchronized void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
    }

    public synchronized void broadcastMsg(String msg){
        for (ClientHandler clientHandler: clients) {
            clientHandler.sendMsg(msg);
        }
    }

    public synchronized boolean isNickBusy(String nick){
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getClass().equals(nick)){
                return  true;
            }
        }
        return false;
    }

    public  AuthService getAuthService(){
        return authService;
    }
}
