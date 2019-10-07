package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.GameLogic;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.interfaces.IGameLogic;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGameLogic {

    @Getter
    private List<Player> players = new ArrayList<>();

    @Getter
    private List<Card> stack = new ArrayList<>();

    @Getter
    private List<Card> deck = new ArrayList<>();
}
