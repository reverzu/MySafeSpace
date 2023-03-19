package com.ak20emccs003.sparivaarsafe_parivaar.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientThread extends Thread {

    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String locationData = in.readLine(); // receive location data from client
            // store the location data in a database or file
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
