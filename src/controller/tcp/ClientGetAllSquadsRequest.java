package controller.tcp;

import com.google.gson.Gson;
import controller.OnlineData;
import controller.client.TCPClient;
import controller.squad.Squad;

import java.util.ArrayList;

public class ClientGetAllSquadsRequest extends TCPClientRequest{

    private TCPClient tcpClient;
    private Gson gson;

    public ClientGetAllSquadsRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        initGson();
    }

    private void initGson() {
        gson = new Gson();
    }

    @Override
    public void checkRequest() {
        ArrayList<Squad> squads;
        synchronized (OnlineData.getSquads()) {
            squads = (ArrayList<Squad>) OnlineData.getSquads().clone();
        }
        tcpClient.getTcpMessager().sendMessage(gson.toJson(squads));
    }
}
