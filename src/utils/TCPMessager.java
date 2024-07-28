package utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPMessager {

    private PrintWriter writer;
    private Scanner scanner;
    private Socket socket;


    public TCPMessager(Socket socket){
        this.socket = socket;
        initVariables();
    }

    private void initVariables() {
        try {
            this.scanner = new Scanner(socket.getInputStream());
            this.writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message){
        writer.println(message);
        writer.flush();
    }

    public void sendMessage(Object object){
        writer.println(new Gson().toJson(object));
        writer.flush();
    }

    public String readMessage(){
        return scanner.nextLine();
    }

    public void close(){
        scanner.close();
        writer.close();
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean hasMessage() {
        return scanner.hasNextLine();
    }
}
