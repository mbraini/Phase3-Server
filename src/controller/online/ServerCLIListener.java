package controller.online;

import controller.online.squad.Squad;

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
