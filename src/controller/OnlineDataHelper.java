package controller;

import controller.squad.Squad;

import java.util.ArrayList;

public class OnlineDataHelper {

    public static Squad findSquad(String squadName) {
        ArrayList<Squad> squads;
        synchronized (OnlineData.getSquads()) {
            squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
        }

        for (Squad squad : squads) {
            if (squad.getName().equals(squadName))
                return squad;
        }
        return null;
    }

}
