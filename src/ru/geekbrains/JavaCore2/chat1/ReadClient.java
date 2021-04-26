package ru.geekbrains.JavaCore2.chat1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadClient extends Thread{

    private Socket socket;
    ReadClient(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try(DataInputStream in = new DataInputStream(socket.getInputStream())) {
            while (true) {
                String str = in.readUTF();
                System.out.println(str);
                if (str.equals("/end")) {
                    return;
                }
            }
        }catch (IOException e){
            System.out.println(e.fillInStackTrace());
        }
    }
}
