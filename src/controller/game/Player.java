package controller.game;

public class Player {

    private Game game;
    private String username;
    private ViewRequestController viewRequestController;
    private ModelRequestController modelRequestController;
    private PlayerData playerData;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ViewRequestController getViewRequestController() {
        return viewRequestController;
    }

    public void setViewRequestController(ViewRequestController viewRequestController) {
        this.viewRequestController = viewRequestController;
    }

    public ModelRequestController getModelRequestController() {
        return modelRequestController;
    }

    public void setModelRequestController(ModelRequestController modelRequestController) {
        this.modelRequestController = modelRequestController;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }
}
