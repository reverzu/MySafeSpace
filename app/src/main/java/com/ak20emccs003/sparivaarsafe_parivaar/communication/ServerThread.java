package com.ak20emccs003.sparivaarsafe_parivaar.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private ServerSocket serverSocket;

    public void run() {
        try {
            serverSocket = new ServerSocket(1234); // replace with your desired port number
            while (true) {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
