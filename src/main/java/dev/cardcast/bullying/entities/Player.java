package dev.cardcast.bullying.entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Player {
    private String name;
    private Hand hand;
    private boolean hasDrawn;
}
