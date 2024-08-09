package controller.online;

import constants.CostConstants;
import controller.online.client.GameClient;
import controller.online.dataBase.OnlineData;
import controller.online.squad.Squad;
import controller.online.squad.SquadBattle;
import controller.online.squad.SquadBattleHistoryMember;

import java.util.ArrayList;
import java.util.Random;
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
                defineWinners();
            }
        }
    }

    private void defineWinners() {
        ArrayList<Squad> squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
        for (Squad squad : squads) {
            if (squad.getSquadBattle().getInBattleWith() != null) {
                Squad winner = defineWinner(squad ,OnlineData.getSquad(squad.getSquadBattle().getInBattleWith()));
                reward(winner ,OnlineData.getSquad(winner.getSquadBattle().getInBattleWith()));
                addHistory(winner ,OnlineData.getSquad(winner.getSquadBattle().getInBattleWith()));
                resetSquadBattle(squad ,OnlineData.getSquad(squad.getSquadBattle().getInBattleWith()));
            }
        }
    }

    private void addHistory(Squad winner, Squad loser) {
        winner.addHistory(new SquadBattleHistoryMember(
                winner.getName(),
                loser.getName(),
                true
        ));
        loser.addHistory(new SquadBattleHistoryMember(
                loser.getName(),
                winner.getName(),
                false
        ));
    }

    private void resetSquadBattle(Squad squad1, Squad squad2) {
        squad1.getSquadBattle().setInBattleWith(null);
        squad2.getSquadBattle().setInBattleWith(null);
    }

    private void reward(Squad winner, Squad loser) {
        int winnerGefjonValue = 1;
        int loserGefjonValue = 1;
        boolean loserHasPalioxis = false;
        if (winner.getTreasury().getGefjonCount() > 0) {
            winner.getTreasury().setGefjonCount(winner.getTreasury().getGefjonCount() - 1);
            winnerGefjonValue = 2;
        }
        if (loser.getTreasury().getGefjonCount() > 0) {
            loser.getTreasury().setGefjonCount(loser.getTreasury().getGefjonCount() - 1);
            loserGefjonValue = 2;
        }
        if (loser.getTreasury().getPalioxisCount() > 0) {
            loserHasPalioxis = true;
            loser.getTreasury().setPalioxisCount(loser.getTreasury().getPalioxisCount() - 1);
        }
        if (winner.getTreasury().getAdonisCount() > 0) {
            winner.getTreasury().setAdonisCount(winner.getTreasury().getAdonisCount() - 1);
        }
        if (loser.getTreasury().getAdonisCount() > 0) {
            loser.getTreasury().setAdonisCount(loser.getTreasury().getAdonisCount() - 1);
        }
        synchronized (winner.getMembers()) {
            for (String member : winner.getMembers()) {
                GameClient gameClient = OnlineData.getGameClient(member);
                gameClient.setXp(gameClient.getXp() + (CostConstants.BATTLE_SQUAD_WIN_PRIZE * winnerGefjonValue));
            }
        }
        synchronized (loser.getMembers()) {
            for (String member : loser.getMembers()) {
                GameClient gameClient = OnlineData.getGameClient(member);
                if (!loserHasPalioxis)
                    gameClient.setXp(gameClient.getXp() - (CostConstants.BATTLE_SQUAD_LOSE_PUNISH * loserGefjonValue));
                else
                    gameClient.setXp(gameClient.getXp() - CostConstants.PALIOXIS_XP_LOST);
                if (gameClient.getXp() < 0)
                    gameClient.setXp(0);
            }
        }
    }

    private Squad defineWinner(Squad squad1, Squad squad2) {
        SquadBattle squadBattle1 = squad1.getSquadBattle();
        SquadBattle squadBattle2 = squad2.getSquadBattle();
        if (squadBattle1.getXpEarned() > squadBattle2.getXpEarned())
            return squad1;
        if (squadBattle2.getXpEarned() > squadBattle1.getXpEarned())
            return squad2;
        if (squadBattle1.getMonomachiaWins() > squadBattle2.getMonomachiaWins())
            return squad1;
        if (squadBattle2.getMonomachiaWins() > squadBattle1.getMonomachiaWins())
            return squad1;
        if (squad1.getTreasury().getGefjonCount() > 0 && squad2.getTreasury().getGefjonCount() == 0)
            return squad1;
        if (squad2.getTreasury().getGefjonCount() > 0 && squad1.getTreasury().getGefjonCount() == 0)
            return squad2;
        Random random = new Random();
        if (random.nextInt(0 ,2) == 0)
            return squad1;
        return squad2;
    }

    private void startSquadBattle() {
        ArrayList<Squad> squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
        for (int i = 0; i < squads.size() ;i = i + 2) {
            if (i + 1 >= squads.size())
                continue;
            squads.get(i).setSquadBattle(new SquadBattle(
                    squads.get(i + 1).getName(),
                    squads.get(i).getTreasury().hasAdonis(),
                    squads.get(i).getMembers()
            ));
            squads.get(i).notifySquadBattle(squads.get(i + 1).getName());
            squads.get(i + 1).setSquadBattle(new SquadBattle(
                    squads.get(i).getName(),
                    squads.get(i + 1).getTreasury().hasAdonis(),
                    squads.get(i + 1).getMembers()
            ));
            squads.get(i + 1).notifySquadBattle(squads.get(i).getName());
        }
    }
}
