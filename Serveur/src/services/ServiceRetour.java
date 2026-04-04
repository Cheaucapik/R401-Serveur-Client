package services;

import documents.ADocument;
import entities.Abonne;
import entities.Document;
import entities.Mediatheque;

import java.time.Duration;
import java.time.LocalDateTime;

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

            ADocument adoc = (ADocument) doc;
            Abonne emprunteur = adoc.getEmpruntePar();

            if (emprunteur == null) {
                try {
                    doc.retour();
                } catch (Exception e) {
                    dialogue.envoyerInfo("Erreur : " + e.getMessage());
                }
            } else {
                LocalDateTime dateDebut = adoc.getDateEmpruntDebut();

                String repDeg = dialogue.demanderOuiNon("Le document est-il en bon etat");
                boolean degrade = repDeg != null && repDeg.equalsIgnoreCase("n");

                try {
                    doc.retour();
                    dialogue.envoyerInfo("Retour du " + doc.getClass().getSimpleName() + " " + doc.getTitre() + " valide");
                    System.out.println("Retour du document " + doc.getId() + " " + doc.getTitre() + " valide pour le client " + clientIP + ":" + clientPort);

                    //SUJET: Duration.between(dateDebut, LocalDateTime.now()).toDays() > 14
                    //TEST(30 sec):
                    boolean retard = dateDebut != null &&
                        Duration.between(dateDebut, LocalDateTime.now()).toSeconds() > 30;
                    if (retard || degrade) {
                        emprunteur.setBanni();
                        String raison = retard ? "retard de plus de 2 semaines" : "document degrade";
                        String dateBanFormatee = emprunteur.getDateBanFin()
                            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy 'a' HH:mm"));
                        dialogue.envoyerInfo("Attention : " + emprunteur.getNom() +
                            " est banni de la mediatheque jusqu'au " + dateBanFormatee +
                            " (" + raison + ")");
                        System.out.println("Abonne " + emprunteur.getNom() + " banni jusqu'au " + emprunteur.getDateBanFin());
                    }
                } catch (Exception e) {
                    dialogue.envoyerInfo("Erreur : " + e.getMessage());
                }
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
