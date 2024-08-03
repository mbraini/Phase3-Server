package model.logics;

import constants.DistanceConstants;
import constants.SizeConstants;
import constants.TimeConstants;
import model.interfaces.movementIntefaces.FrameAttacher;
import model.objectModel.ObjectModel;
import model.objectModel.frameModel.FrameLocations;
import model.objectModel.frameModel.FrameModel;
import utils.FrameHelper.FrameCalculationHelper;
import utils.Vector;

import java.util.ArrayList;

public class FrameHit {
    private FrameModel frame;
    private ObjectModel model;
    private ArrayList<ObjectModel> models;

    public FrameHit(FrameModel frame , ObjectModel model ,ArrayList<ObjectModel> models){
        this.frame = frame;
        this.model = model;
        this.models = models;
    }

    public void handle() {
        FrameLocations frameLocation = FrameCalculationHelper.findClosestLocalFrameLocation(
                frame,
                model.getPosition()
        );
        /////////////////conditions.....
        resize(frame ,frameLocation);

    }

    private void resize(FrameModel frame, FrameLocations frameLocation) {
        switch (frameLocation){
            case top :
                topHitIf();
                for (ObjectModel model1 : models) {
                    if (model1 instanceof FrameAttacher) {
                        FrameLocations frameLocations = ((FrameAttacher) model1).getAttachedLocation();
                        if
                        (
                                frameLocations == FrameLocations.top
                                || frameLocations == FrameLocations.topLeft
                                || frameLocations == FrameLocations.topRight
                        )
                            ((FrameAttacher) model1).damage();
                    }
                }
                break;
            case bottom:
                bottomHitIf();
                for (ObjectModel model1 : models) {
                    if (model1 instanceof FrameAttacher) {
                        FrameLocations frameLocations = ((FrameAttacher) model1).getAttachedLocation();
                        if
                        (
                                frameLocations == FrameLocations.bottom
                                        || frameLocations == FrameLocations.bottomLeft
                                        || frameLocations == FrameLocations.bottomRight
                        )
                            ((FrameAttacher) model1).damage();
                    }
                }
                break;
            case right:
                rightHitIf();
                for (ObjectModel model1 : models) {
                    if (model1 instanceof FrameAttacher) {
                        FrameLocations frameLocations = ((FrameAttacher) model1).getAttachedLocation();
                        if
                        (
                                frameLocations == FrameLocations.right
                                        || frameLocations == FrameLocations.topRight
                                        || frameLocations == FrameLocations.bottomRight
                        )
                            ((FrameAttacher) model1).damage();
                    }
                }
                break;
            case left:
                leftHitIf();
                for (ObjectModel model1 : models) {
                    if (model1 instanceof FrameAttacher) {
                        FrameLocations frameLocations = ((FrameAttacher) model1).getAttachedLocation();
                        if
                        (
                                frameLocations == FrameLocations.left
                                        || frameLocations == FrameLocations.topLeft
                                        || frameLocations == FrameLocations.bottomLeft
                        )
                            ((FrameAttacher) model1).damage();
                    }
                }
                break;
        }
    }

    private void topHitIf() {
        Vector framePosition = frame.getPosition();
        if (framePosition.y <= 0)
            return;
        new FrameHitPerformer(
                frame,
                DistanceConstants.FRAME_BULLET_RESIZE,
                0,
                0,
                0,
                TimeConstants.FRAME_SHRINKAGE_TIME
        ).frameHit();
    }

    private void bottomHitIf() {
        Vector framePosition = frame.getPosition();
        if (framePosition.y >= SizeConstants.SCREEN_SIZE.height)
            return;
        new FrameHitPerformer(
                frame,
                0,
                DistanceConstants.FRAME_BULLET_RESIZE,
                0,
                0,
                TimeConstants.FRAME_SHRINKAGE_TIME
        ).frameHit();
    }

    private void leftHitIf() {
        Vector framePosition = frame.getPosition();
        if (framePosition.x <= 0)
            return;
        new FrameHitPerformer(
                frame,
                0,
                0,
                0,
                DistanceConstants.FRAME_BULLET_RESIZE,
                TimeConstants.FRAME_SHRINKAGE_TIME
        ).frameHit();
    }

    private void rightHitIf() {
        Vector framePosition = frame.getPosition();
        if (framePosition.x >= SizeConstants.SCREEN_SIZE.width)
            return;
        new FrameHitPerformer(
                frame,
                0,
                0,
                DistanceConstants.FRAME_BULLET_RESIZE,
                0,
                TimeConstants.FRAME_SHRINKAGE_TIME
        ).frameHit();
    }


}
