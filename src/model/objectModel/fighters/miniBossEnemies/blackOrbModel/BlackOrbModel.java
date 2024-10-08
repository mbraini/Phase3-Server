package model.objectModel.fighters.miniBossEnemies.blackOrbModel;

import constants.SizeConstants;
import controller.game.onlineGame.Game;
import controller.game.enums.AbstractEnemyType;
import controller.game.manager.Spawner;
import controller.game.player.Player;
import controller.online.annotations.SkippedByJson;
import model.objectModel.effects.BlackOrbAoeEffectModel;
import model.objectModel.fighters.AbstractEnemy;
import model.objectModel.fighters.finalBoss.bossAI.ImaginaryObject;
import model.objectModel.frameModel.FrameModel;
import utils.Math;
import utils.Vector;

import java.util.ArrayList;

public class BlackOrbModel extends AbstractEnemy {

    @SkippedByJson
    private BlackOrbThread blackOrbThread;
    private ArrayList<OrbModel> orbModels;
    private ArrayList<FrameModel> frameModels;
    private ArrayList<BlackOrbAoeEffectModel> effectModels = new ArrayList<>();
    private Vector center;
    private int frameCount;
    private int orbCount;

    public BlackOrbModel(Game game , Player chasingPlayer ,ArrayList<Player> targetedPlayer, Vector center , String id){
        super(game ,chasingPlayer ,targetedPlayer);
        this.id = id;
        frameModels = new ArrayList<>();
        orbModels = new ArrayList<>();
        blackOrbThread = new BlackOrbThread(this);
        type = AbstractEnemyType.blackOrb;
        this.center = new Vector(
                SizeConstants.SCREEN_SIZE.width / 2d,
                SizeConstants.SCREEN_SIZE.height / 2d
        );
        addSolidObject();
        spawn();
    }

    private void addSolidObject() {
        ArrayList<Vector> vertices = new ArrayList<>();
        Vector firstVertex = Math.VectorAdd(
                center,
                new Vector(0 ,SizeConstants.BLACK_ORB_DIAGONAL_SIZE + SizeConstants.ORB_DIMENSION.height)
        );
        for (int i = 0 ;i < 5 ;i++) {
            vertices.add(firstVertex);
            firstVertex = Math.RotateByTheta(firstVertex ,center , java.lang.Math.PI / 5);
        }
        game.addSolidObject(new ImaginaryObject(
                game,
                vertices,
                id
        ));
    }

    public ArrayList<OrbModel> getOrbModels() {
        return orbModels;
    }

    public void setOrbModels(ArrayList<OrbModel> orbModels) {
        this.orbModels = orbModels;
    }

    public ArrayList<FrameModel> getFrameModels() {
        return frameModels;
    }

    public void setFrameModels(ArrayList<FrameModel> frameModels) {
        this.frameModels = frameModels;
    }


    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public int getOrbCount() {
        return orbCount;
    }

    public void setOrbCount(int orbCount) {
        this.orbCount = orbCount;
    }

    public void addFrame(FrameModel frameModel){
        frameModels.add(frameModel);
        frameCount++;
    }

    public void addOrb(OrbModel orbModel){
        orbModels.add(orbModel);
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }


    public void spawn() {
        start();
    }


    public BlackOrbThread getBlackOrbThread() {
        return blackOrbThread;
    }

    public void setBlackOrbThread(BlackOrbThread blackOrbThread) {
        this.blackOrbThread = blackOrbThread;
    }

    public ArrayList<BlackOrbAoeEffectModel> getEffectModels() {
        return effectModels;
    }

    public void addEffect(BlackOrbModel blackOrbModel ,OrbModel orbOrigin, OrbModel orbDestination, String id) {
        BlackOrbAoeEffectModel effectModel = new BlackOrbAoeEffectModel(
                blackOrbModel.game,
                targetedPlayers,
                blackOrbModel ,
                orbOrigin ,
                orbDestination ,
                id
        );
        effectModels.add(effectModel);
        Spawner.addBlackOrbEffectModel(game ,effectModel);
    }

    public void removeOrb(String id) {
        for (OrbModel orbModel : orbModels){
            if (orbModel.getId().equals(id)){
                orbModels.remove(orbModel);
                return;
            }
        }
    }


    public void start(){
        blackOrbThread = new BlackOrbThread(this);
        blackOrbThread.start();
    }


    public void checkDeath() {
        if (orbModels.isEmpty()) {
            game.getModelRequests().removeAbstractEnemy(id);
            game.removeSolidObject(id);
        }
    }
}
