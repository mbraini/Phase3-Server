package controller.tcp.requests.getSquadMembers;

import com.google.gson.Gson;
import controller.OnlineData;
import controller.client.ClientState;
import controller.client.TCPClient;
import controller.squad.Squad;
import controller.tcp.ServerMessageType;
import controller.tcp.TCPClientRequest;

import java.util.ArrayList;

public class ClientGetSquadMembersRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientGetSquadMembersRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        initGson();
    }

    private void initGson() {
        gson = new Gson();
    }

    @Override
    public void checkRequest() {
        Squad squad = OnlineData.getSquad(tcpClient.getUsername());
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
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.getSquadMembers);
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
    }
}
