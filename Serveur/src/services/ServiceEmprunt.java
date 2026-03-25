package services;

import entities.Abonne;
import entities.Document;
import entities.Mediatheque;

import java.io.IOException;
import java.net.Socket;

public class ServiceEmprunt implements Runnable {
    private Socket socket;
    private Mediatheque mediatheque;
    public ServiceEmprunt(Socket s, Mediatheque m) {
        this.socket = s;
        this.mediatheque = m;
    }

    @Override
    public void run() {
        try {
            Dialogue dialogue = new Dialogue(socket, mediatheque);

            String clientIP = socket.getInetAddress().getHostAddress();
            int clientPort = socket.getPort();
            System.out.println("Service d'emprunt lancé pour le client : " + clientIP + ":" + clientPort);

            dialogue.envoyerInfo("Bienvenue sur le service d'emprunt !");

            Abonne abonne = dialogue.demanderAbonne();
            if (abonne == null) return;
            Document doc = dialogue.demanderDocument();
            if (doc == null) return;

            try {
                doc.emprunt(abonne);
                dialogue.envoyerInfo("Emprunt du " + doc.getClass().getSimpleName() + " " + doc.getTitre() + " valide");
                System.out.println("Emprunt du document " + doc.getId() + " " + doc.getTitre() + " valide pour le client " + clientIP + ":" + clientPort);
            } catch (Exception e) {
                dialogue.envoyerInfo("Erreur : " + e.getMessage());
            }

            dialogue.envoyerInfo("Fin de la session d'emprunt.");
            System.out.println("Fin de la session emprunt pour le client : " + clientIP + ":" + clientPort);

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
