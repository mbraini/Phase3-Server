package controller.online;

import constants.ControllerConstants;
import constants.SizeConstants;
import controller.game.Game;
import controller.game.GameType;
import controller.game.player.Player;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import model.objectModel.fighters.basicEnemies.TrigorathModel;
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
                System.out.println("WHO WON?");
                ///todo
            }
            else if (command.equals("start test player")) {
                Game game = new Game();
                game.setGameType(GameType.monomachia);
                Player player = new Player(game ,"test");
                game.addPlayer(player);
                OnlineData.putClientOnlineGame("test" ,game);
                OnlineData.getTCPClient("test").getTcpMessager().sendMessage(ServerMessageType.getPorts);
            }
            else if (command.equals("start game")) {
                OnlineData.getOnlineGame("test").start();
            }
            else if (command.equals("add archmire")) {
                OnlineData.getOnlineGame("test").getModelRequests().addObjectModel(new ArchmireModel(
                        OnlineData.getOnlineGame("test"),
                        new Vector(-600 ,-600),
                        Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
                ));
            }
            else if (command.equals("add wyrm")) {
                WyrmModel wyrmModel =new WyrmModel(
                        OnlineData.getOnlineGame("test"),
                        new Vector(SizeConstants.SCREEN_SIZE.width / 2d - 100,SizeConstants.SCREEN_SIZE.height / 2d - 100),
                        Helper.RandomStringGenerator(ControllerConstants.ID_SIZE)
                );
                OnlineData.getOnlineGame("test").getModelRequests().addObjectModel(wyrmModel);
                OnlineData.getOnlineGame("test").getModelRequests().addFrameModel(wyrmModel.getFrameModel());}
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
