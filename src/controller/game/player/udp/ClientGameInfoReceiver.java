package controller.game.player.udp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.game.player.Player;
import utils.Helper;
import utils.Vector;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientGameInfoReceiver extends Thread{

    private final Player player;
    private final int port;
    private final DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private volatile JGameInfoSenderHelper lastClientGameInfo = new JGameInfoSenderHelper();
    private Gson gson;

    public ClientGameInfoReceiver(int port ,Player player) {
        this.port = port;
        this.player = player;
        gson = new Gson();

        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        datagramPacket = new DatagramPacket(new byte[10000] ,10000);
    }

    @Override
    public void run() {

        while (!isInterrupted()) {
            try {
                datagramSocket.receive(datagramPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JGameInfoSenderHelper clientGameInfo;
            try {
                String JObjects = new String(Helper.getDataUntil(datagramPacket.getData() ,datagramPacket.getLength()));
                clientGameInfo = gson.fromJson(JObjects , JGameInfoSenderHelper.class);
            }
            catch (Exception e) {
                continue;
            }
            if (lastClientGameInfo.equals(clientGameInfo))
                continue;
            if (clientGameInfo.getLastAim() != null) {
                player.getViewRequestController().shootRequest(clientGameInfo.getLastAim());
            }
            if (clientGameInfo.getLastMousePosition() != null) {
                player.getViewRequestController().rotateRequest(clientGameInfo.lastMousePosition);
            }
            if (!clientGameInfo.releasedKeys.isEmpty() || !clientGameInfo.pressedKeys.isEmpty()) {
                player.getViewRequestController().movementRequest(
                        clientGameInfo.pressedKeys,
                        clientGameInfo.releasedKeys
                );
            }
            if (!clientGameInfo.typedKeys.isEmpty()) {
                player.getViewRequestController().keyTypedHandler(clientGameInfo.typedKeys);
            }
            lastClientGameInfo = clientGameInfo;
        }

    }


    public static class JGameInfoSenderHelper {

        private ArrayList<Integer> pressedKeys;
        private ArrayList<Integer> releasedKeys;
        private Vector lastAim;
        private Vector lastMousePosition;
        private ArrayList<Character> typedKeys;
        public JGameInfoSenderHelper() {
            pressedKeys = new ArrayList<>();
            releasedKeys = new ArrayList<>();
            typedKeys = new ArrayList<>();
        }

        public ArrayList<Integer> getPressedKeys() {
            return pressedKeys;
        }

        public void setPressedKeys(ArrayList<Integer> pressedKeys) {
            this.pressedKeys = pressedKeys;
        }

        public ArrayList<Integer> getReleasedKeys() {
            return releasedKeys;
        }

        public void setReleasedKeys(ArrayList<Integer> releasedKeys) {
            this.releasedKeys = releasedKeys;
        }

        public Vector getLastAim() {
            return lastAim;
        }

        public void setLastAim(Vector lastAim) {
            this.lastAim = lastAim;
        }

        public Vector getLastMousePosition() {
            return lastMousePosition;
        }

        public void setLastMousePosition(Vector lastMousePosition) {
            this.lastMousePosition = lastMousePosition;
        }

        public ArrayList<Character> getTypedKeys() {
            return typedKeys;
        }

        public void setTypedKeys(ArrayList<Character> typedKeys) {
            this.typedKeys = typedKeys;
        }

        public boolean equals(JGameInfoSenderHelper helper) {
            if (lastAim == null) {
                if (helper.lastAim != null)
                    return false;
            }
            if (lastMousePosition == null) {
                if (helper.lastMousePosition != null)
                    return false;
            }
            if (helper.lastAim == null) {
                if (lastAim != null)
                    return false;
            }
            if (helper.lastMousePosition == null) {
                if (lastMousePosition != null)
                    return false;
            }
            if (lastAim != null && helper.lastAim != null) {
                if (!lastAim.Equals(helper.lastAim))
                    return false;
            }
            if (lastMousePosition != null && helper.lastMousePosition != null) {
                if (!lastMousePosition.Equals(helper.lastMousePosition))
                    return false;
            }
            if (pressedKeys.size() != helper.pressedKeys.size())
                return false;
            if (releasedKeys.size() != helper.releasedKeys.size())
                return false;
            if (typedKeys.size() != helper.typedKeys.size())
                return false;
            for (int i = 0 ;i < pressedKeys.size() ;i++){
                if (pressedKeys.get(i).intValue() != helper.pressedKeys.get(i).intValue())
                    return false;
            }
            for (int i = 0 ;i < releasedKeys.size() ;i++){
                if (releasedKeys.get(i).intValue() != helper.releasedKeys.get(i).intValue())
                    return false;
            }
            for (int i = 0 ;i < typedKeys.size() ;i++){
                if (typedKeys.get(i).charValue() != helper.typedKeys.get(i).charValue())
                    return false;
            }
            return true;
        }

    }

}
