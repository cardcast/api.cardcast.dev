package dev.cardcast.bullying.network.handlers;

import dev.cardcast.bullying.Bullying;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    private final int port;
    private ServerSocket serverSocket;


    public GameServer(int port) throws IOException {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Bullying.getLogger().info("Couldn't start server socket");
            e.printStackTrace();
            return;
        }

        while (true) {
            Socket socket = this.serverSocket.accept();
            System.out.println("New client connected");

            new ServerThread(socket).start();
        }
    }



}
