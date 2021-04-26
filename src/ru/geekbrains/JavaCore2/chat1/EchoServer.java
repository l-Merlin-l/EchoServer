package ru.geekbrains.JavaCore2.chat1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        Socket socket;
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            socket = serverSocket.accept();

            Thread threadIn = new Thread(new ReadClient(socket));
            Thread threadOut = new Thread(new WriteClient(socket));

            threadIn.start();
            threadOut.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
