package ru.geekbrains.JavaCore2.chat2;

public interface AuthService {
    void start();
    String getNickLoginPass(String login, String password);
    void stop();
}
