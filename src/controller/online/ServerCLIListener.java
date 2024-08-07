package controller.online;

import constants.ControllerConstants;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.GameType;
import controller.game.enums.ModelType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import model.objectModel.fighters.basicEnemies.TrigorathModel;
import model.objectModel.fighters.miniBossEnemies.blackOrbModel.BlackOrbModel;
import model.objectModel.fighters.normalEnemies.archmireModel.ArchmireModel;
import model.objectModel.fighters.normalEnemies.wyrmModel.WyrmModel;
import model.objectModel.frameModel.FrameModel;
import utils.Helper;
import utils.Vector;

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
                Game game = new Game(GameType.colosseum);
                Player player = new Player(game ,"test");
                OnlineData.putClientPlayer("test" ,player);
                OnlineData.putClientOnlineGame("test" ,game);
                OnlineData.getTCPClient("test").getTcpMessager().sendMessage(ServerMessageType.getPorts);
            }
            else if (command.equals("start test2 player")) {
                Player player = new Player(OnlineData.getOnlineGame("test") ,"test2");
                OnlineData.putClientPlayer("test2" ,player);
                OnlineData.putClientOnlineGame("test2" ,OnlineData.getOnlineGame("test"));
                OnlineData.getTCPClient("test2").getTcpMessager().sendMessage(ServerMessageType.getPorts);
            }
            else if (command.equals("start game")) {
                Game game = OnlineData.getOnlineGame("test");
                OnlineData.getOnlineGame("test").start();
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
