package dev.cardcast.bullying.network.handlers;

import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThread extends Thread {


    private Socket socket;
    private PrintWriter writer;

    public ServerThread(Socket socket, Player player) {
        this.socket = socket;
        Bullying.getNetworkManager().addClient(player, this);
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            this.writer = new PrintWriter(output, true);

            int i = 10;
            do {
                String message = reader.readLine();
                ServerBoundWSMessage packet = Bullying.getNetworkManager().parsePayload(message);

                i++;
            } while (i != 0); // add on exit packet

            socket.close();
        } catch (IOException ex) {
            Bullying.getLogger().warning("SERVER EXCEPTION: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
