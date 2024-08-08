package controller.online;

import controller.game.Game;
import controller.game.player.Player;
import controller.online.dataBase.OnlineData;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;

import java.util.ArrayList;
import java.util.Scanner;

public class ServerCLIListener extends Thread {

    private final Scanner scanner;

    public ServerCLIListener() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("initiateSquadBattle")) {
                startSquadBattle();
            }
            else if (command.equals("terminateSquadBattle")) {
                ///todo
            }
            else if (command.equals("start test player")) {

            }
            else if (command.equals("start test2 player")) {
                Player player = new Player(OnlineData.getOnlineGame("test") ,"test2");
                OnlineData.putClientPlayer("test2" ,player);
                OnlineData.putClientOnlineGame("test2" ,OnlineData.getOnlineGame("test"));
                OnlineData.getTCPClient("test2").getTcpMessager().sendMessage(ServerMessageType.getPorts);
            }
            else if (command.equals("start game")) {
                Game game = OnlineData.getOnlineGame("test");
                game.start();
            }
        }
    }

    private void startSquadBattle() {
        ArrayList<Squad> squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
        for (int i = 0; i < squads.size() ;i = i + 2) {
            if (i + 1 >= squads.size())
                continue;
            squads.get(i).getSquadBattle().setInBattleWith(squads.get(i + 1).getName());
            squads.get(i + 1).getSquadBattle().setInBattleWith(squads.get(i).getName());
        }
    }
}
