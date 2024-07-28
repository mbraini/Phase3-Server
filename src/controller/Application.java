package controller;

import controller.tcp.ServerWorker;

import java.io.IOException;

public class Application implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 20 ;i++) {
            new ServerWorker().start();
        }
        try {
            ServerWorker.serverWorker.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
