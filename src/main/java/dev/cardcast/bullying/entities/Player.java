package dev.cardcast.bullying.entities;

import lombok.Getter;

import java.util.UUID;

public class Player {

    private final UUID uuid;

    @Getter
    private String name;

    @Getter
    private Hand hand;

    public Player(UUID uuid) {
        this.uuid = uuid;
    }
}
