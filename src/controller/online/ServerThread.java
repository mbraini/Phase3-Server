package controller.online;

import constants.RefreshRateConstants;
import controller.online.client.ClientState;
import controller.online.client.TCPClient;
import controller.online.tcp.messages.ClientMessage;

import java.util.ArrayList;

public class ServerThread extends Thread {

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(RefreshRateConstants.SERVER_THREAD_REFRESH_RATE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateServer();
        }

    }

    private void updateServer() {
        /////save info
        //todo
        /////

        sendMessagesIf();


    }

    private void sendMessagesIf() {
        ArrayList<String> clientUsernames;
        synchronized (OnlineData.getClientUsernames()) {
            clientUsernames = (ArrayList<String>) OnlineData.getClientUsernames().clone();
        }
        for (String username : clientUsernames) {
            TCPClient tcpClient = OnlineData.getTCPClient(username);
            if (tcpClient.getClientState().equals(ClientState.online)) {
                if (!tcpClient.getMessages().isEmpty()) {
                    for (ClientMessage clientMessage : tcpClient.getMessages()) {
                        clientMessage.deliverMessage();
                    }
                    tcpClient.setMessages(new ArrayList<>());
                }
            }
        }
    }
}
