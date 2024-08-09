package controller.online.squad;


import controller.online.dataBase.OnlineData;
import controller.online.tcp.messages.squadBattleNotification.ClientSquadBattleNotificationMessage;

import java.util.ArrayList;

public class Squad {

    private String name;
    private SquadBattle squadBattle;
    private Treasury treasury;
    private ArrayList<String> members;
    private ArrayList<SquadBattleHistoryMember> squadBattleHistory = new ArrayList<>();
    private String owner;

    public Squad(String name) {
        this.name = name;
        members = new ArrayList<>();
        squadBattle = new SquadBattle();
        treasury = new Treasury();
        owner = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }


    public synchronized void addMember(String username) {
        members.add(username);
        OnlineData.putClientSquadMap(username ,this);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public synchronized void removeMember(String username) {
        members.remove(username);
        if (owner.equals(username)) {
            owner = "";
            if (!members.isEmpty())
                owner = members.getFirst();
            else {
                OnlineData.removeSquad(this);
            }
        }
    }

    public SquadBattle getSquadBattle() {
        return squadBattle;
    }

    public void setSquadBattle(SquadBattle squadBattle) {
        this.squadBattle = squadBattle;
    }

    public Treasury getTreasury() {
        return treasury;
    }

    public void setTreasury(Treasury treasury) {
        this.treasury = treasury;
    }

    public void addHistory(SquadBattleHistoryMember historyMember) {
        synchronized (squadBattleHistory) {
            squadBattleHistory.add(historyMember);
        }
    }

    public ArrayList<SquadBattleHistoryMember> getSquadBattleHistory() {
        synchronized (squadBattleHistory) {
            return squadBattleHistory;
        }
    }

    public void notifySquadBattle(String enemySquad) {
        synchronized (members) {
            for (String member : members) {
                OnlineData.getTCPClient(member).addMessage(
                        new ClientSquadBattleNotificationMessage(member ,enemySquad)
                );
            }
        }
    }
}
