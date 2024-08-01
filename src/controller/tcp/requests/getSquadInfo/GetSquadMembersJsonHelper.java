package controller.tcp.requests.getSquadInfo;

import controller.client.ClientState;

public class GetSquadMembersJsonHelper {
    private String username;
    private int xp;
    private ClientState clientState;

    public GetSquadMembersJsonHelper(String username, int xp, ClientState clientState) {
        this.username = username;
        this.xp = xp;
        this.clientState = clientState;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }
}
