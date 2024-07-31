package controller;

import constants.RefreshRateConstants;
import controller.client.ClientState;
import controller.client.TCPClient;
import controller.tcp.messages.ClientMessage;

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
        ArrayList<TCPClient> clients;
        synchronized (OnlineData.getClients()) {
            clients = (ArrayList<TCPClient>) OnlineData.getClients().clone();
        }
        for (TCPClient client : clients) {
            if (client.getClientState().equals(ClientState.online)) {
                if (!client.getMessages().isEmpty()) {
                    for (ClientMessage clientMessage : client.getMessages()) {
                        clientMessage.deliverMessage();
                    }
                    client.setMessages(new ArrayList<>());
                }
            }
        }
    }
}
