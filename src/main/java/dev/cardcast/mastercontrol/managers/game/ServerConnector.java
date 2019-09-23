package dev.cardcast.mastercontrol.managers.game;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;

public abstract class ServerConnector extends Thread {

    @Getter
    private final int port;

    @Getter
    private final String host;

    @Getter
    private DataOutputStream outputStream;

    @Getter
    private DataInputStream dataInputStream;

    @Getter
    private Socket socket;

    public ServerConnector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(this.host, this.port);
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (!socket.isClosed()) {
                this.onMessage(br.readLine());
            }
            this.outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract void onMessage(String message);

    @SneakyThrows
    public void sendMessage(String message) {
        this.outputStream.writeBytes(message);
        this.outputStream.flush();
    }
}
