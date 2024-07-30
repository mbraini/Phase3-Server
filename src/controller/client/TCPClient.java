package controller.client;

import controller.SkippedByJson;
import controller.squad.Squad;
import utils.TCPMessager;
import java.net.Socket;

public class TCPClient {
    private String username;
    private String ip;
    private Squad squad;
    @SkippedByJson
    private volatile ClientState clientState;
    @SkippedByJson
    private final TCPMessager tcpMessager;

    public TCPClient(Socket socket) {
        tcpMessager = new TCPMessager(socket);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }


    public TCPMessager getTcpMessager() {
        return tcpMessager;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }
}
