package dev.cardcast.bullying.network.handlers;

import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.entities.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class GameServer {

    private final int port;
    private ServerSocket serverSocket;

    public GameServer(int port) throws IOException {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
            Bullying.getLogger().info("Started server socket on port " + port);
        } catch (IOException e) {
            Bullying.getLogger().info("Couldn't start server socket");
            e.printStackTrace();
            return;
        }

        while (true) {
            Socket socket = this.serverSocket.accept();
            System.out.println("New client connected");
            Player player = new Player(UUID.randomUUID());
            new ServerThread(socket, player).start();
        }
    }


}
