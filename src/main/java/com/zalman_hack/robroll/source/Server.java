package com.zalman_hack.robroll.source;

import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Properties;

public class Server {
    public static final int PORT = 8080;
    public static LinkedList<ServerProcess> serverList = new LinkedList<>();
    public static MessageHistory messageHistory;

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("src/log4j.properties"));
        PropertyConfigurator.configure(props);

        ServerSocket server = new ServerSocket(PORT);
        messageHistory = new MessageHistory();
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                RequestHandler.handleRequest(socket);
                try {
                    serverList.add(new ServerProcess(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
