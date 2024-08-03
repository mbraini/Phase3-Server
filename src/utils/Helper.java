package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.DistanceConstants;
import constants.SizeConstants;
import model.objectModel.frameModel.FrameModel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Helper {
    private static final Random random = new Random();
    public static ArrayList<Double> giveMaxMin(ArrayList<Double> a){
        ArrayList<Double> answer = new ArrayList<>();
        double min = a.get(0);
        double max = a.get(0);
        for (int i = 0 ;i < a.size() ;i++){
            if (a.get(i) < min){
                min = a.get(i);
            }
            if (a.get(i) > max){
                max = a.get(i);
            }
        }
        answer.add(min);
        answer.add(max);
        return answer;
    }

    public static Double PointDistanceWithLine(Vector vert1 ,Vector vert2 ,Vector point){
        Vector u = Math.VectorWithSize(Math.VectorAdd(vert2 ,Math.ScalarInVector(-1 ,vert1)) ,1);
        Vector a = Math.ScalarInVector(0.5 ,Math.VectorAdd(vert1 ,vert2));
        Vector aPoint = Math.VectorAdd(Math.ScalarInVector(-1 ,point) ,a);
        return Math.VectorSize(Math.CrossProduct(u ,aPoint));
    }

    public static String RandomStringGenerator(int n){
        byte[] str = new byte[n];
        new Random().nextBytes(str);
        return new String(str);
    }

    public static Vector createRandomPosition(FrameModel frameModel ,boolean inFrame) {
        if (inFrame) {
            int x = random.nextInt(
                    (int) frameModel.getPosition().x,
                    (int) frameModel.getPosition().x + frameModel.getSize().width
            );
            int y = random.nextInt(
                    (int) frameModel.getPosition().y,
                    (int) frameModel.getPosition().y + frameModel.getSize().height
            );
            return new Vector(x, y);
        }
        else {
            int x = random.nextInt(
                    -DistanceConstants.ENEMY_SPAWN_MARGIN ,
                    frameModel.getSize().width + DistanceConstants.ENEMY_SPAWN_MARGIN
            );
            int y = 0;
            if (x < -DistanceConstants.ENEMY_SPAWN_MARGIN || x > frameModel.getSize().width + DistanceConstants.ENEMY_SPAWN_MARGIN){
                y = random.nextInt(-DistanceConstants.ENEMY_SPAWN_MARGIN ,frameModel.getSize().height + DistanceConstants.ENEMY_SPAWN_MARGIN);
            }
            else {
                int rand = random.nextInt(2);
                if (rand == 0){
                    y = random.nextInt(-DistanceConstants.ENEMY_SPAWN_MARGIN ,0);
                }
                else {
                    y = random.nextInt(
                            frameModel.getSize().height + DistanceConstants.ENEMY_SPAWN_MARGIN ,
                            frameModel.getSize().height + DistanceConstants.ENEMY_SPAWN_MARGIN + SizeConstants.TRIGORATH_DIMENTION.height
                    );
                }
            }
            return new Vector(x ,y);
        }
    }

    public static Vector createRandomPositionSeparately(FrameModel epsilonFrame, Dimension size) {
        Vector solution;

        double frameRightX = SizeConstants.SCREEN_SIZE.width - epsilonFrame.getPosition().getX() - epsilonFrame.getSize().width;
        double frameLeftX = epsilonFrame.getPosition().getX();
        double frameTopY = epsilonFrame.getPosition().getY();
        double frameBottomY = SizeConstants.SCREEN_SIZE.height - epsilonFrame.getPosition().getY() - epsilonFrame.getSize().height;
        frameRightX = java.lang.Math.abs(frameRightX);
        frameLeftX = java.lang.Math.abs(frameLeftX);
        frameTopY = java.lang.Math.abs(frameTopY);
        frameBottomY = java.lang.Math.abs(frameBottomY);
        double max = java.lang.Math.max((java.lang.Math.max(frameRightX ,frameLeftX)) , java.lang.Math.max(frameTopY ,frameBottomY));

        if (max == frameLeftX) {
            solution = new Vector(
                    epsilonFrame.getPosition().x - size.width,
                    (2 * epsilonFrame.getPosition().y + epsilonFrame.getSize().height) / 2d
            );
        }
        else if (max == frameRightX) {
            solution = new Vector(
                    epsilonFrame.getPosition().x + epsilonFrame.getSize().width + size.width,
                    (2 * epsilonFrame.getPosition().y + epsilonFrame.getSize().height) / 2d
            );
        }

        else if (max == frameTopY) {
            solution = new Vector(
                    (2 * epsilonFrame.getPosition().x + epsilonFrame.getSize().width) / 2d,
                    epsilonFrame.getPosition().y - size.height
            );
        }

        else {
            solution = new Vector(
                    (2 * epsilonFrame.getPosition().x + epsilonFrame.getSize().width) / 2d,
                    epsilonFrame.getPosition().y + epsilonFrame.getSize().height + size.height            );
        }
        return solution;
    }

    public static void resetAllJsons(String path) {
        Helper.writeFile(path + "/models.json" ,"");
    }

    public static synchronized StringBuilder readFile(String path) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine())
            stringBuilder.append(scanner.nextLine());
        scanner.close();
        return stringBuilder;
    }

    public static synchronized void writeFile(String path ,String text) {
        try {
            PrintWriter printWriter = new PrintWriter(path);
            printWriter.write(text);
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
