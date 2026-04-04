package services;

import documents.ADocument;
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

            try {
                doc.reservation(abonne);
                dialogue.envoyerInfo("Reservation du " + doc.getClass().getSimpleName() + " " + doc.getTitre() + " validee");
                System.out.println("Réservation du document " + doc.getId() + " " + doc.getTitre() + " validee pour le client " + clientIP + ":" + clientPort);
            } catch (Exception e) {
                long secondesRestantes = ((ADocument) doc).getSecondesRestantesReservation();
                if (secondesRestantes > 0 && secondesRestantes <= 60) {
                    dialogue.envoyerInfo("Le document est presque disponible... musique celeste en cours (" + secondesRestantes + "s)");
                    System.out.println("Grand Chaman : attente expiration reservation pour le client " + clientIP + ":" + clientPort);
                    long debutAttente = System.currentTimeMillis();
                    long limiteMs = (secondesRestantes + 5) * 1000L;
                    while (((ADocument) doc).isReservationActive()) {
                        if (System.currentTimeMillis() - debutAttente > limiteMs) break;
                        try { Thread.sleep(200); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
                    }
                    try {
                        doc.reservation(abonne);
                        dialogue.envoyerInfo("Reservation du " + doc.getClass().getSimpleName() + " " + doc.getTitre() + " validee apres attente !");
                        System.out.println("Grand Chaman : reservation validee apres attente pour " + clientIP + ":" + clientPort);
                    } catch (Exception e2) {
                        dialogue.envoyerInfo("Desole, le document est toujours reserve ou a ete emprunte entre-temps. Concert celeste gratuit ! Vous auriez du faire une offrande plus importante au grand chaman.");
                        System.out.println("Grand Chaman : echec apres attente (" + e2.getClass().getSimpleName() + ": " + e2.getMessage() + ")");
                    }
                } else {
                    dialogue.envoyerInfo("Erreur : " + e.getMessage());
                }
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
