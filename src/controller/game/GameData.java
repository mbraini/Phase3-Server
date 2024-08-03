package controller.game;

import java.util.HashMap;

public class GameData {

    public static HashMap<String ,Game> idGameMap;

    public static Game getGame(String id) {
        return idGameMap.get(id);
    }


}
