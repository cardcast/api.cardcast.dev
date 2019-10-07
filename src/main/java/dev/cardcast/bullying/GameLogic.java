package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.interfaces.IBullyLogic;
import lombok.Getter;

public class GameLogic /*implements IBullyLogic */{

    private static GameLogic instance = null;

    private GameLogic(){}

    public static GameLogic getInstance()
    {
        if (instance == null){
            instance = new GameLogic();
        }
        return instance;
    }




}
