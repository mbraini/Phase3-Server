//package utils.FrameHelper;
//
//import model.logics.collision.Collision;
//import model.objectModel.frameModel.FrameLocations;
//import model.objectModel.frameModel.FrameModel;
//import utils.Vector;
//
//public class FrameCalculationHelper {
//
//    public static FrameLocations findClosestLocalFrameLocation(FrameModel frameModel , Vector position){
//        double top = frameModel.getPosition().getY();
//        double bottom = frameModel.getPosition().getY() + frameModel.getSize().height;
//        double left = frameModel.getPosition().getX();
//        double right = frameModel.getPosition().getX() + frameModel.getSize().width;
//
//        double topDistance = Math.abs(position.y - top);
//        double bottomDistance = Math.abs(position.y - bottom);
//        double leftDistance = Math.abs(position.x - left);
//        double rightDistance = Math.abs(position.x - right);
//        double min = Math.min(
//                    Math.min(leftDistance ,rightDistance),
//                    Math.min(topDistance ,bottomDistance)
//            );
//
//        if (min == topDistance){
//            return FrameLocations.top;
//        }
//        else if (min == bottomDistance){
//            return FrameLocations.bottom;
//        }
//        else if (min == leftDistance){
//            return FrameLocations.left;
//        }
//        else {
//            return FrameLocations.right;
//        }
//
//    }
//
//    public static double findClosestDistanceToFrameEdges(Vector position ,FrameModel frameModel) {
//
//        double top = frameModel.getPosition().getY();
//        double bottom = frameModel.getPosition().getY() + frameModel.getSize().height;
//        double left = frameModel.getPosition().getX();
//        double right = frameModel.getPosition().getX() + frameModel.getSize().width;
//
//        double topDistance = Math.abs(position.y - top);
//        double bottomDistance = Math.abs(position.y - bottom);
//        double leftDistance = Math.abs(position.x - left);
//        double rightDistance = Math.abs(position.x - right);
//        return Math.min(
//                Math.min(leftDistance ,rightDistance),
//                Math.min(topDistance ,bottomDistance)
//        );
//    }
//
//    public static void setFrameDisables(FrameModel frame1 ,FrameModel frame2){
//        setFrameDisablesRelativeTo(frame1 ,frame2);
//        setFrameDisablesRelativeTo(frame2 ,frame1);
//    }
//
//    private static void setFrameDisablesRelativeTo(FrameModel target, FrameModel related) {
//        for (int i = (int) target.getPosition().x ;i < target.getPosition().x + target.getSize().width ;i++){
//            Vector vectorTop = new Vector(i ,target.getPosition().y);
//            Vector vectorBottom = new Vector(i ,target.getPosition().y + target.getSize().height);
//            if (Collision.isInFrame(related , vectorTop)){
//                target.setCanTopResize(false);
//            }
//            if (Collision.isInFrame(related , vectorBottom)){
//                target.setCanBottomResize(false);
//            }
//        }
//        for (int i = (int) target.getPosition().y ;i < target.getPosition().y + target.getSize().height ;i++){
//            Vector vectorLeft = new Vector(target.getPosition().x ,i);
//            Vector vectorRight = new Vector(target.getPosition().x + target.getSize().width ,i);
//            if (Collision.isInFrame(related , vectorLeft)){
//                target.setCanLeftResize(false);
//            }
//            if (Collision.isInFrame(related , vectorRight)){
//                target.setCanRightResize(false);
//            }
//        }
//    }
//}
