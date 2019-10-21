package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;

import java.util.Collection;
import java.util.Collections;

public class GameLogic {
    private static GameLogic instance = null;

    private GameLogic(){}

    public static GameLogic getInstance()
    {
        if (instance == null){
            instance = new GameLogic();
        }
        return instance;
    }

    public void ShuffleCards(Game game){
        Collections.shuffle(game.getDeck());
    }

    public void distributeCards(Game game){
        for (Player player:game.getPlayers()) {
            for(int i=0;i<=7;i++){
                //todo
            }
        }
    }

    public void PlayCard(Game game, Card card){
        //todo checks
    }

    public void DrawCard(Game game, int playernr, int amount){

    }
}
