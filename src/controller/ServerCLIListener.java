package controller;

import java.util.Scanner;

public class ServerCLIListener extends Thread {

    private final Scanner scanner;

    public ServerCLIListener() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("initiateSquadBattle")) {
                System.out.println("FIGHT!");
                ///todo
            }
            else if (command.equals("terminateSquadBattle")) {
                System.out.println("WHO WON?");
                ///todo
            }
        }
    }
}
