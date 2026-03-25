package services;

import entities.Abonne;
import entities.Document;
import entities.Mediatheque;

import java.io.IOException;
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
            Dialogue dialogue = new Dialogue(socket, mediatheque);

            String clientIP = socket.getInetAddress().getHostAddress();
            int clientPort = socket.getPort();
            System.out.println("Service de réservation lancé pour le client : " + clientIP + ":" + clientPort);

            dialogue.envoyerInfo("Bienvenue sur le service de reservation !");

            Abonne abonne = dialogue.demanderAbonne();
            if (abonne == null) return;
            Document doc = dialogue.demanderDocument();
            if (doc == null) return;

            try{
                doc.reservation(abonne);
                dialogue.envoyerInfo("Reservation du " + doc.getClass().getSimpleName() + " " + doc.getTitre() +  " validee");
                System.out.println("Réservation du document " + doc.getId() + " " + doc.getTitre() +  " validee pour le client " + clientIP + ":" + clientPort );
            }
            catch(Exception e) {
                dialogue.envoyerInfo("Erreur : " + e.getMessage());
            }

            dialogue.envoyerInfo("Fin de la session de reservation.");
            System.out.println("Fin de la session reservation pour le client : " + clientIP + ":" + clientPort);

        } catch (IOException e) {
            System.out.println("Le client s'est déconnecté (Connection reset).");
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException ex) {
            }
        }
    }

}
