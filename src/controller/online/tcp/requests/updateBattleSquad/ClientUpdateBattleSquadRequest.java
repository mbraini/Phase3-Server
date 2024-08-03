package controller.online.tcp.requests.updateBattleSquad;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.online.OnlineData;
import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.squad.Squad;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClientUpdateBattleSquadRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientUpdateBattleSquadRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        initGson();
    }

    private void initGson() {
        gson = new Gson();
    }

    @Override
    public void checkRequest() {
        String JEnemySquadName = tcpClient.getTcpMessager().readMessage();
        String JMembers = tcpClient.getTcpMessager().readMessage();
        String JThisPlayer = tcpClient.getTcpMessager().readMessage();
        String JEnemyMembers = tcpClient.getTcpMessager().readMessage();

        String enemySquadName = JEnemySquadName;
        Type type = new TypeToken<ArrayList<GetBattleSquadMemberHelper>>(){}.getType();
        ArrayList<GetBattleSquadMemberHelper> thisSquadMembers = gson.fromJson(JMembers ,type);
        ArrayList<GetBattleSquadMemberHelper> enemySquadMembers = gson.fromJson(JEnemyMembers ,type);
        GetBattleSquadMemberHelper thisPlayer = gson.fromJson(JThisPlayer ,GetBattleSquadMemberHelper.class);

        Squad squad = OnlineData.getClientSquad(tcpClient.getUsername());
        if (squad.getSquadBattle().getInBattleWith() == null)
            return;
        if (updateNeeded(enemySquadName ,thisSquadMembers ,thisPlayer ,enemySquadMembers)) {
            update();
        }
        else {
            tcpClient.getTcpMessager().sendMessage(ServerMessageType.updateBattleSquad);
            tcpClient.getTcpMessager().sendMessage(ServerRecponceType.no);
        }

    }

    private void update() {
        Squad mySquad = OnlineData.getClientSquad(tcpClient.getUsername());
        Squad enemySquad = OnlineData.getSquad(mySquad.getSquadBattle().getInBattleWith());
        String squadName = enemySquad.getName();
        ArrayList<GetBattleSquadMemberHelper> onTimeMySquadMembers = getOnTimeMembers(mySquad);
        ArrayList<GetBattleSquadMemberHelper> onTimeEnemySquadMembers = getOnTimeMembers(enemySquad);
        GetBattleSquadMemberHelper onTimeThisPlayer = getOnlineThisPlayer();

        tcpClient.getTcpMessager().sendMessage(ServerMessageType.updateBattleSquad);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.yes);
        tcpClient.getTcpMessager().sendMessage(squadName);
        tcpClient.getTcpMessager().sendMessage(gson.toJson(onTimeMySquadMembers));
        tcpClient.getTcpMessager().sendMessage(gson.toJson(onTimeThisPlayer));
        tcpClient.getTcpMessager().sendMessage(gson.toJson(onTimeEnemySquadMembers));
    }

    private boolean updateNeeded(
            String enemySquadName,
            ArrayList<GetBattleSquadMemberHelper> mySquadMembers,
            GetBattleSquadMemberHelper thisPlayer ,
            ArrayList<GetBattleSquadMemberHelper> enemySquadMembers
    )
    {
        Squad mySquad = OnlineData.getClientSquad(tcpClient.getUsername());
        Squad enemySquad = OnlineData.getSquad(mySquad.getSquadBattle().getInBattleWith());
        ArrayList<GetBattleSquadMemberHelper> onTimeMySquadMembers = getOnTimeMembers(mySquad);
        ArrayList<GetBattleSquadMemberHelper> onTimeEnemySquadMembers = getOnTimeMembers(enemySquad);
        GetBattleSquadMemberHelper onTimeThisPlayer = getOnlineThisPlayer();
        if (!enemySquadName.equals(enemySquad.getName()))
            return true;
        if (!onTimeThisPlayer.equals(thisPlayer))
            return true;
        if (onTimeMySquadMembers.size() != mySquadMembers.size() || onTimeEnemySquadMembers.size() != enemySquadMembers.size())
            return true;
        for (int i = 0; i < onTimeMySquadMembers.size() ; i++) {
            if (!onTimeMySquadMembers.get(i).equals(mySquadMembers.get(i)))
                return true;
        }
        for (int i = 0; i < onTimeEnemySquadMembers.size() ; i++) {
            if (!onTimeEnemySquadMembers.get(i).equals(enemySquadMembers.get(i)))
                return true;
        }
        return false;
    }

    public ArrayList<GetBattleSquadMemberHelper> getOnTimeMembers(Squad squad) {
        ArrayList<GetBattleSquadMemberHelper> onTimeMembers = new ArrayList<>();
        for (String member : squad.getMembers()) {
            if (member.equals(this.tcpClient.getUsername()))
                continue;
            TCPClient memberTCP = OnlineData.getTCPClient(member);
            int xp = OnlineData.getGameClient(member).getXp();
            ClientState clientState = memberTCP.getClientState();
            onTimeMembers.add(
                    new GetBattleSquadMemberHelper(
                            member,
                            xp,
                            clientState,
                            false,
                            false
                    )
            );
        }
        return onTimeMembers;
    }

    public GetBattleSquadMemberHelper getOnlineThisPlayer() {
        return new GetBattleSquadMemberHelper(
                tcpClient.getUsername(),
                OnlineData.getGameClient(tcpClient.getUsername()).getXp(),
                tcpClient.getClientState(),
                false,
                false
        );
    }


}
