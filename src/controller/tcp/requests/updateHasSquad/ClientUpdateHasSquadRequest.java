package controller.tcp.requests.updateHasSquad;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.OnlineData;
import controller.client.ClientState;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.ServerRecponceType;
import controller.tcp.TCPClientRequest;
import controller.tcp.requests.updateHasSquad.GetSquadMembersJsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClientUpdateHasSquadRequest extends TCPClientRequest {

    private Gson gson;
    private TCPClient tcpClient;

    public ClientUpdateHasSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        initGson();
    }

    private void initGson() {
        gson = new Gson();
    }

    @Override
    public void checkRequest() {

        String JMembers = tcpClient.getTcpMessager().readMessage();
        String JThisPlayer = tcpClient.getTcpMessager().readMessage();
        Type type = new TypeToken<ArrayList<GetSquadMembersJsonHelper>>(){}.getType();
        ArrayList<GetSquadMembersJsonHelper> members = gson.fromJson(JMembers ,type);
        GetSquadMembersJsonHelper thisPlayer = gson.fromJson(JThisPlayer ,GetSquadMembersJsonHelper.class);

        if (updateNeeded(members ,thisPlayer)) {
            update();
        }
        else {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.updateHasSquad);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.no);
        }
    }

    private void update() {
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.updateHasSquad);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.yes);
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());;
        ArrayList<GetSquadMembersJsonHelper> members = getOnTimeMembers(squad);
        GetSquadMembersJsonHelper thisPlayer = getOnlineThisPlayer();

        tcpClient.getTcpMessager().sendMessage(gson.toJson(members));
        tcpClient.getTcpMessager().sendMessage(gson.toJson(thisPlayer));
        tcpClient.getTcpMessager().sendMessage(squad.getName());
        if (squad.getOwner().equals(tcpClient.getUsername()))
            tcpClient.getTcpMessager().sendMessage(true);
        else
            tcpClient.getTcpMessager().sendMessage(false);
    }

    private boolean updateNeeded(ArrayList<GetSquadMembersJsonHelper> members, GetSquadMembersJsonHelper thisPlayer) {
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());;
        ArrayList<GetSquadMembersJsonHelper> onTimeMembers = getOnTimeMembers(squad);
        GetSquadMembersJsonHelper onTimeThisPlayer = getOnlineThisPlayer();
        if (!onTimeThisPlayer.equals(thisPlayer))
            return true;
        if (onTimeMembers.size() != members.size())
            return true;
        for (int i = 0 ;i < onTimeMembers.size() ;i++) {
            if (!onTimeMembers.get(i).equals(members.get(i)))
                return true;
        }
        return false;
    }

    public ArrayList<GetSquadMembersJsonHelper> getOnTimeMembers(Squad squad) {
        ArrayList<GetSquadMembersJsonHelper> onTimeMembers = new ArrayList<>();
        for (String member : squad.getMembers()) {
            if (member.equals(this.tcpClient.getUsername()))
                continue;
            TCPClient memberTCP = OnlineData.getTCPClient(member);
            int xp = OnlineData.getGameClient(member).getXp();
            ClientState clientState = memberTCP.getClientState();
            onTimeMembers.add(
                    new GetSquadMembersJsonHelper(
                            member,
                            xp,
                            clientState
                    )
            );
        }
        return onTimeMembers;
    }

    public GetSquadMembersJsonHelper getOnlineThisPlayer() {
        return new GetSquadMembersJsonHelper(
                tcpClient.getUsername(),
                OnlineData.getGameClient(tcpClient.getUsername()).getXp(),
                tcpClient.getClientState()
        );
    }

}
