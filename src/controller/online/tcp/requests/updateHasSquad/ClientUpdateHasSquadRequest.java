package controller.online.tcp.requests.updateHasSquad;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.online.dataBase.OnlineData;
import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

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
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        ArrayList<GetSquadMembersJsonHelper> members = getOnTimeMembers(squad);
        GetSquadMembersJsonHelper thisPlayer = getOnlineThisPlayer();
        if (squad != null) {
            tcpClient.getTcpMessager().sendMessage(gson.toJson(members));
            tcpClient.getTcpMessager().sendMessage(gson.toJson(thisPlayer));
            tcpClient.getTcpMessager().sendMessage(squad.getName());
            if (squad.getOwner().equals(tcpClient.getUsername()))
                tcpClient.getTcpMessager().sendMessage(true);
            else
                tcpClient.getTcpMessager().sendMessage(false);
        }
        else {
            tcpClient.getTcpMessager().sendMessage(gson.toJson(members));
            tcpClient.getTcpMessager().sendMessage(gson.toJson(thisPlayer));
            tcpClient.getTcpMessager().sendMessage("you have no squad");
            tcpClient.getTcpMessager().sendMessage(false);
        }
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
        if (squad == null)
            return onTimeMembers;
        for (String member : squad.getMembers()) {
            if (member.equals(this.tcpClient.getUsername()))
                continue;
            TCPClient memberTCP = OnlineData.getTCPClient(member);
            int xp = OnlineData.getGameClient(member).getXp();
            ClientState clientState;
            if (memberTCP == null)
                clientState = ClientState.offline;
            else
                clientState = memberTCP.getClientState();
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
