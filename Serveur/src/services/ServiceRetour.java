package services;

import entities.Document;
import serveur.Dialogue;
import serveur.Mediatheque;

import java.io.IOException;
import java.net.Socket;

public class ServiceRetour implements Runnable {
    private Socket socket;
    Mediatheque mediatheque;
    public ServiceRetour(Socket s, Mediatheque m) {
        this.socket = s;
        this.mediatheque = m;
    }

    @Override
    public void run() {
        try {
            Dialogue dialogue = new Dialogue(socket, mediatheque);

            String clientIP = socket.getInetAddress().getHostAddress();
            int clientPort = socket.getPort();
            System.out.println("Service de retour lancé pour le client : " + clientIP + ":" + clientPort);

            dialogue.envoyerInfo("Bienvenue sur le service de retour !");

            Document doc = dialogue.demanderDocument();
            if (doc == null) return;

            try{
                doc.retour();
                dialogue.envoyerInfo("Retour du " + doc.getClass().getSimpleName() + " " + doc.getTitre() +  " valide");
                System.out.println("Retour du document " + doc.getId() + " " + doc.getTitre() +  " valide pour le client " + clientIP + ":" + clientPort );
            }
            catch(Exception e) {
                dialogue.envoyerInfo("Erreur : " + e.getMessage());
            }

            dialogue.envoyerInfo("Fin de la session de retour.");
            System.out.println("Fin de la session retour pour le client : " + clientIP + ":" + clientPort);


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
