package ru.geekbrains.JavaCore2.chat2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyServer {
    private static MyServer server;

    public static MyServer getServer() {
        return server;
    }

    private final int PORT = 8080;
    private Map<String, ClientHandler> clients;
    private AuthService authService;

    public MyServer() {
        server = this;
        try (ServerSocket server = new ServerSocket(PORT)){
            authService = new BaseAuthService();
            authService.start();
            clients = new HashMap<>();
            while (true){
                Socket socket = server.accept();
                new ClientHandler(socket);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler.getName());
    }

    public synchronized void subscribe(ClientHandler clientHandler){
        clients.put(clientHandler.getName(), clientHandler);
    }

    public synchronized void broadcastMsg(String msg){
        clients.forEach((k, client) -> client.sendMsg(msg));
    }

    public synchronized void privateMsg(String name, String fromName, String msg){
        if(clients.containsKey(fromName)){
            clients.get(fromName).sendMsg("Личное сообщение от " + name + ": " + msg);
            clients.get(name).sendMsg("Личное сообщение для " + fromName + ": " + msg);
        }
    }

    public synchronized boolean isNickBusy(String nick){
        return clients.containsKey(nick);
    }

    public  AuthService getAuthService(){
        return authService;
    }
}
