package dev.cardcast.mastercontrol;

import dev.cardcast.mastercontrol.managers.GameManager;
import dev.cardcast.mastercontrol.managers.SocketManager;
import lombok.Getter;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        new Main();
    }

    @Getter
    private GameManager gameManager;

    @Getter
    private SocketManager socketManager;

    private Main() {
        try {
            this.gameManager = new GameManager();
            this.socketManager = new SocketManager(this.gameManager, 6969);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}