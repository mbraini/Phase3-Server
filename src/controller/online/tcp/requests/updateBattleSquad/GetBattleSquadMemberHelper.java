package controller.online.tcp.requests.updateBattleSquad;

import controller.online.client.ClientState;

public class GetBattleSquadMemberHelper {

    private String username;
    private int xp;
    private ClientState clientState;
    private boolean playedColosseum;
    private boolean playedMonomachia;

    public GetBattleSquadMemberHelper() {

    }

    public GetBattleSquadMemberHelper(String username, int xp, ClientState clientState , boolean playedMonomachia, boolean playedColosseum) {
        this.username = username;
        this.xp = xp;
        this.clientState = clientState;
        this.playedMonomachia = playedMonomachia;
        this.playedColosseum = playedColosseum;
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

    public boolean playedColosseum() {
        return playedColosseum;
    }

    public void setPlayedColosseum(boolean playedColosseum) {
        this.playedColosseum = playedColosseum;
    }

    public boolean playedMonomachia() {
        return playedMonomachia;
    }

    public void setPlayedMonomachia(boolean playedMonomachia) {
        this.playedMonomachia = playedMonomachia;
    }

    public boolean equals(GetBattleSquadMemberHelper helper) {
        if (getXp() == helper.getXp() && getClientState().equals(helper.getClientState())
                && getUsername().equals(helper.getUsername()) && playedMonomachia == helper.playedMonomachia()
                && playedColosseum == helper.playedColosseum()
        )
            return true;
        return false;
    }

}
