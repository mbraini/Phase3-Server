package controller.online.tcp.requests.updateInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.online.client.GameClient;
import controller.online.client.TCPClient;
import controller.online.dataBase.OnlineData;
import controller.online.tcp.ServerMessageType;
import controller.online.tcp.ServerRecponceType;
import controller.online.tcp.TCPClientRequest;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ClientUpdateInfoRequest extends TCPClientRequest {

    private TCPClient tcpClient;
    private Gson gson;

    public ClientUpdateInfoRequest(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        gson = new Gson();
    }

    @Override
    public void checkRequest() {
        String JGameHistories = tcpClient.getTcpMessager().readMessage();
        String JSkillTreeHistories = tcpClient.getTcpMessager().readMessage();
        Type historyType = new TypeToken<ArrayList<GameHistoryHelper>>(){}.getType();
        Type skillTree = new TypeToken<ArrayList<SkillTreeBuyHelper>>(){}.getType();
        ArrayList<GameHistoryHelper> gameHistories = gson.fromJson(JGameHistories ,historyType);
        ArrayList<SkillTreeBuyHelper> skillTreeHistories = gson.fromJson(JSkillTreeHistories ,skillTree);
        check(gameHistories ,skillTreeHistories);
    }

    public void check(ArrayList<GameHistoryHelper> gameHistories, ArrayList<SkillTreeBuyHelper> skillTreeHistories) {
        for (GameHistoryHelper gameHistory : gameHistories) {
            String hash = findGameHistoryHash(gameHistory);
            if (!hash.equals(gameHistory.getHash())) {
                sendCheat();
                return;
            }
            if (gameHistory.getTimePassed() == 0) {
                sendCheat();
                return;
            }
            if (gameHistory.getXpGained() / gameHistory.timePassed >= 100) { ///:)
                sendCheat();
                return;
            }
        }
        for (SkillTreeBuyHelper skillTreeBuyHelper : skillTreeHistories) {
            String hash = findSkillTreeBuyHash(skillTreeBuyHelper);
            if (!hash.equals(skillTreeBuyHelper.hash)) {
                sendCheat();
                return;
            }
        }
        update(gameHistories ,skillTreeHistories);
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.matchHistory);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.done);
    }

    private void update(ArrayList<GameHistoryHelper> gameHistories, ArrayList<SkillTreeBuyHelper> skillTreeHistories) {
        int xpDelta = 0;
        GameClient gameClient = OnlineData.getGameClient(tcpClient.getUsername());
        for (GameHistoryHelper gameHistory : gameHistories) {
            xpDelta = xpDelta + gameHistory.getXpGained();
            for (InGameAbilityBuyHelper buyHelper : gameHistory.getAbilityBuyHelpers()) {
                xpDelta = xpDelta - buyHelper.getCost();
            }
            for (InGameSkillTreeBuyHelper buyHelper : gameHistory.getInGameSkillTreeBuyHelpers()) {
                xpDelta = xpDelta - buyHelper.getCost();
            }
        }
        for (SkillTreeBuyHelper skillTreeBuyHelper : skillTreeHistories) {
            xpDelta = xpDelta - skillTreeBuyHelper.xpCost;
            switch (skillTreeBuyHelper.getAbilityType()) {
                case ares :
                    gameClient.setAres(true);
                    break;
                case aceso:
                    gameClient.setAceso(true);
                    break;
                case proteus:
                    gameClient.setProteus(true);
                    break;
                case melampus:
                    gameClient.setMelampus(true);
                    break;
                case athena:
                    gameClient.setAthena(true);
                    break;
                case empusa:
                    gameClient.setEmpusa(true);
                    break;
                case dolus:
                    gameClient.setDolus(true);
                    break;
                case chiron:
                    gameClient.setChiron(true);
                    break;
                case astrape:
                    gameClient.setAstrape(true);
                    break;
                case cerberus:
                    gameClient.setCerberus(true);
                    break;
            }
        }
        gameClient.setXp(gameClient.getXp() + xpDelta);
    }

    private String findSkillTreeBuyHash(SkillTreeBuyHelper skillTreeBuyHelper) {
        SkillTreeBuyHelper newSkillTreeHelper = new SkillTreeBuyHelper(
                skillTreeBuyHelper.xpCost,
                skillTreeBuyHelper.abilityType
        );
        String hash;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(gson.toJson(newSkillTreeHelper).getBytes());
            hash = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return hash;
    }

    private String findGameHistoryHash(GameHistoryHelper gameHistory) {
        GameHistoryHelper newHistory = new GameHistoryHelper();
        newHistory.setAbilityBuyHelpers(gameHistory.getAbilityBuyHelpers());
        newHistory.setInGameSkillTreeBuyHelpers(gameHistory.getInGameSkillTreeBuyHelpers());
        newHistory.setTimePassed(gameHistory.getTimePassed());
        newHistory.setXpGained(gameHistory.getXpGained());
        String hash;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(gson.toJson(newHistory).getBytes());
            hash = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return hash;
    }

    public void sendCheat() {
        tcpClient.getTcpMessager().sendMessage(ServerMessageType.matchHistory);
        tcpClient.getTcpMessager().sendMessage(ServerRecponceType.error);
    }


}
