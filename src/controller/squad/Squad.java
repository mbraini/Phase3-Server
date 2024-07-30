package controller.squad;


import controller.client.TCPClient;

import java.util.ArrayList;

public class Squad {

    private String name;
    private ArrayList<TCPClient> members;
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
}
