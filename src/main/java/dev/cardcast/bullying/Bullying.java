package dev.cardcast.bullying;

import dev.cardcast.bullying.network.NetworkService;
import lombok.Getter;

import java.util.logging.Logger;

public class Bullying {

    @Getter
    private static final Logger logger = Logger.getLogger("BULLYING");

    public Bullying(String[] args) {
        NetworkService networkService = new NetworkService();
    }


}
