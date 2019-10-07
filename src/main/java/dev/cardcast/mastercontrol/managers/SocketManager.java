package dev.cardcast.mastercontrol.managers;

import dev.cardcast.mastercontrol.GameConnectionPool;
import dev.cardcast.mastercontrol.managers.game.GameServerConnector;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager {

    @Getter
    private final GameManager gameManager;

    public SocketManager(GameManager manager, int port) throws IOException {
        this.gameManager = manager;

        this.start(port);
    }

    private ServerSocket serverSocket;

    private void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            new GameClientHandler(serverSocket.accept()).start();
        }
    }

    private void stop() throws IOException {
        serverSocket.close();
    }

    private static class GameClientHandler extends Thread {

        @Getter
        private Socket clientSocket;

        @Getter
        private PrintWriter out;

        @Getter
        private BufferedReader in;

        GameClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @SneakyThrows
        public void run() {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //todo find gamename or id
            GameServerConnector server = GameConnectionPool.findGameConnectorByName(null);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                server.sendMessage(inputLine);
            }

            in.close();
            out.close();
            clientSocket.close();
        }
    }
}
