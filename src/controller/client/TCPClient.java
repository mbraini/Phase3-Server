package controller.client;

import controller.SkippedByJson;
import controller.squad.Squad;
import controller.tcp.messages.ClientMessage;
import utils.TCPMessager;
import java.net.Socket;
import java.util.ArrayList;

public class TCPClient {
    private String username;
    private String ip;
    private ArrayList<ClientMessage> messages;
    @SkippedByJson
    private volatile ClientState clientState;
    @SkippedByJson
    private final TCPMessager tcpMessager;

    public TCPClient(Socket socket) {
        tcpMessager = new TCPMessager(socket);
        messages = new ArrayList<>();
        clientState = ClientState.online;
        username = "";
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

    public void addMessage(ClientMessage message) {
        messages.add(message);
    }

    public ArrayList<ClientMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ClientMessage> messages) {
        this.messages = messages;
    }
}
