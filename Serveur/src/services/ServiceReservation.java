package services;

import entities.Abonne;
import entities.EmpruntException;
import serveur.Mediatheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceReservation implements Runnable {
    private Socket socket;
    private Mediatheque mediatheque;
    public ServiceReservation(Socket s, Mediatheque m) {
        this.socket = s;
        this.mediatheque = m;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); //Pour envoyer des données
            BufferedReader entree = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); //Ce qu'on reçoit

            writer.println("Veuillez entrer votre numéro d'abonné");
            try{
                int numeroAb = Integer.parseInt(entree.readLine());
                Abonne a = mediatheque.getAbonne(numeroAb);
                if(a == null){
                    writer.println("Erreur : Veuillez entrer un numéro d'abonné valide");
                    return;
                }
            }
            catch(Exception e){
                writer.println("Erreur : Le numéro doit être un entier");
                return;
            }

            writer.println("Veuillez entrer l'identifiant du document");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
