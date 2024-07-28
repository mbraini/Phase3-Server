import controller.tcp.ClientRequestType;
import utils.TCPMessager;

import java.io.IOException;
import java.net.Socket;

public class test {

    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1" ,8090);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TCPMessager messager = new TCPMessager(socket);
        messager.sendMessage(ClientRequestType.signUp);
        messager.sendMessage("mahdi1384");
        messager.sendMessage("barani");

    }

}
