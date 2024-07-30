package controller.squad;


import controller.OnlineData;
import controller.annotations.SkippedForClient;
import controller.client.TCPClient;

import java.util.ArrayList;

public class Squad {

    private String name;
    @SkippedForClient
    private ArrayList<TCPClient> members;
    @SkippedForClient
    private TCPClient owner;

    public Squad(String name) {
        this.name = name;
        members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TCPClient> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<TCPClient> members) {
        this.members = members;
    }

    public synchronized void addMember(TCPClient tcpClient) {
        members.add(tcpClient);
        tcpClient.setSquad(this);
    }
}
