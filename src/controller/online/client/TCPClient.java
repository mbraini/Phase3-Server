package controller.online.client;

import controller.online.annotations.SkippedByJson;
import controller.online.tcp.ConnectionChecker;
import controller.online.tcp.messages.ClientMessage;
import utils.TCPMessager;

import java.net.Socket;
import java.util.ArrayList;

public class TCPClient {
    private String username;
    private String ip;
    private ArrayList<ClientMessage> messages;
    private volatile ClientState clientState;
    @SkippedByJson
    private TCPMessager tcpMessager;
    @SkippedByJson
    private ConnectionChecker connectionChecker;
    @SkippedByJson
    private TCPMessager gameConnection;

    public TCPClient(Socket socket) {
        tcpMessager = new TCPMessager(socket);
        connectionChecker = new ConnectionChecker(this);
        messages = new ArrayList<>();
        clientState = ClientState.online;
        username = "";
    }

    public TCPClient(String username) {
        this.username = username;
        this.clientState = ClientState.offline;
        messages = new ArrayList<>();
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

    public ConnectionChecker getConnectionChecker() {
        return connectionChecker;
    }

    public TCPMessager getGameConnection() {
        return gameConnection;
    }

    public void setGameConnection(TCPMessager gameConnection) {
        this.gameConnection = gameConnection;
    }
}
