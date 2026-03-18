package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceRetour implements Runnable {
    private Socket socket;
    public ServiceRetour(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            BufferedReader entree = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); //Ce qu'on reçoit
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); //Pour envoyer des données

            System.out.println();

            String message = entree.readLine();

            String invMessage = new StringBuffer(message).reverse().toString();

            writer.println(invMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
