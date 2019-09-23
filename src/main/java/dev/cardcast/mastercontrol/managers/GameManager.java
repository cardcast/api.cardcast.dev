package dev.cardcast.mastercontrol.managers;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.cardcast.mastercontrol.GameConnectionPool;
import dev.cardcast.mastercontrol.managers.game.Game;
import dev.cardcast.mastercontrol.utils.Utils;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    @Getter
    private List<Game> games = new ArrayList<>();

    public GameManager() {
        try {
            this.loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() throws IOException {
        String jsonString = Utils.readResource("gameservers.json", Charsets.UTF_8);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonArray array = jsonObject.getAsJsonArray("servers");

        array.forEach(jsonElement -> GameConnectionPool.addGame(Utils.getGson().fromJson(jsonElement.toString(), Game.class)));
    }
}
