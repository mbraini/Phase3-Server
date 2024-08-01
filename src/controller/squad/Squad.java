package controller.squad;


import controller.OnlineData;
import controller.annotations.SkippedForClient;
import controller.client.TCPClient;

import java.util.ArrayList;

public class Squad {

    private String name;
    @SkippedForClient
    private ArrayList<String> members;
    @SkippedForClient
    private String owner;

    public Squad(String name) {
        this.name = name;
        members = new ArrayList<>();
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
        }
    }
}
