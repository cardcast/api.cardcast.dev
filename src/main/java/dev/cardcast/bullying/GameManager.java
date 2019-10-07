package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    @Getter
    private List<Game> games = new ArrayList<>();
}
