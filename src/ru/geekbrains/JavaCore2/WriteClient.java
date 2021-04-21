package ru.geekbrains.JavaCore2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WriteClient extends Thread{
    private Socket socket;

    WriteClient(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try(DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String str = scanner.nextLine();
                out.writeUTF(str);

                if (str.equals("/end")) {
                    break;
                }
            }
        }catch (IOException e){
            System.out.println(e.fillInStackTrace());
        }
    }
}
