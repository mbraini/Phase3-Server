package controller.tcp.requests.getSquadInfo;

import com.google.gson.Gson;
import controller.OnlineData;
import controller.client.ClientState;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.TCPClientRequest;

import java.util.ArrayList;

public class ClientGetSquadInfoRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientGetSquadInfoRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        initGson();
    }

    private void initGson() {
        gson = new Gson();
    }

    @Override
    public void checkRequest() {
        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        ArrayList<GetSquadMembersJsonHelper> helpers = new ArrayList<>();
        for (String member : squad.getMembers()) {
            if (member.equals(this.tcpClient.getUsername()))
                continue;
            TCPClient memberTCP = OnlineData.getTCPClient(member);
            int xp = OnlineData.getGameClient(member).getXp();
            ClientState clientState = memberTCP.getClientState();
            helpers.add(
                    new GetSquadMembersJsonHelper(
                            member,
                            xp,
                            clientState
                    )
            );
        }
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.getSquadInfo);
        tcpClient.getTcpMessager().sendMessage(gson.toJson(helpers));
        tcpClient.getTcpMessager().sendMessage(
                gson.toJson(
                        new GetSquadMembersJsonHelper(
                                tcpClient.getUsername(),
                                OnlineData.getGameClient(tcpClient.getUsername()).getXp(),
                                tcpClient.getClientState()
                        )
                )
        );
        tcpClient.getTcpMessager().sendMessage(squad.getName());
        if (tcpClient.getUsername().equals(squad.getOwner())) {
            tcpClient.getTcpMessager().sendMessage(true);
        }
        else {
            tcpClient.getTcpMessager().sendMessage(false);
        }
    }
}
