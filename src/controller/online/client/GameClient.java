package controller.online.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.online.annotations.SkippedByJson;
import controller.online.client.gameClientUpdate.SkillTreeJsonHelper;
import utils.TCPMessager;

public class GameClient {
    private String username;
    private int xp;
    private boolean ares;
    private boolean astrape;
    private boolean cerberus;
    private boolean aceso;
    private boolean melampus;
    private boolean chiron;
    private boolean athena;
    private boolean proteus;
    private boolean empusa;
    private boolean dolus;
    @SkippedByJson
    private Gson gson;

    public GameClient(String username) {
        this.username = username;
    }

    private void initGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        gson = builder.create();
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public boolean isAres() {
        return ares;
    }

    public void setAres(boolean ares) {
        this.ares = ares;
    }

    public boolean isAstrape() {
        return astrape;
    }

    public void setAstrape(boolean astrape) {
        this.astrape = astrape;
    }

    public boolean isCerberus() {
        return cerberus;
    }

    public void setCerberus(boolean cerberus) {
        this.cerberus = cerberus;
    }

    public boolean isAceso() {
        return aceso;
    }

    public void setAceso(boolean aceso) {
        this.aceso = aceso;
    }

    public boolean isMelampus() {
        return melampus;
    }

    public void setMelampus(boolean melampus) {
        this.melampus = melampus;
    }

    public boolean isChiron() {
        return chiron;
    }

    public void setChiron(boolean chiron) {
        this.chiron = chiron;
    }

    public boolean isAthena() {
        return athena;
    }

    public void setAthena(boolean athena) {
        this.athena = athena;
    }

    public boolean isProteus() {
        return proteus;
    }

    public void setProteus(boolean proteus) {
        this.proteus = proteus;
    }

    public boolean isEmpusa() {
        return empusa;
    }

    public void setEmpusa(boolean empusa) {
        this.empusa = empusa;
    }

    public boolean isDolus() {
        return dolus;
    }

    public void setDolus(boolean dolus) {
        this.dolus = dolus;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public void update(TCPClient tcpClient) {
        initGson();
        TCPMessager TCPmessager = tcpClient.getTcpMessager();
        String xpString = TCPmessager.readMessage();
        xp = Integer.valueOf(xpString);
        String json = TCPmessager.readMessage();
        SkillTreeJsonHelper helper = gson.fromJson(json, SkillTreeJsonHelper.class);
        ares = helper.ares;
        astrape = helper.astrape;
        cerberus = helper.cerberus;
        aceso = helper.aceso;
        melampus = helper.melampus;
        chiron = helper.chiron;
        athena = helper.athena;
        proteus = helper.proteus;
        empusa = helper.empusa;
        dolus = helper.dolus;
    }

}
