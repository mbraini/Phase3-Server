package controller;

import controller.tcp.ServerWorker;

import java.io.IOException;
import java.util.ArrayList;

public class Application implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 20 ;i++) {
            new ServerWorker().start();
        }
        new ServerThread().start();

        /////ports
        ArrayList<Integer> ports = new ArrayList<>();
        for (int i = 8000; i < 9000 ;i++) {
            ports.add(i);
        }
        OnlineData.setAvailablePorts(ports);
        /////

        ////cli
        new ServerCLIListener().start();
        ////
        try {
            ServerWorker.serverWorker.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
