package services;

import entities.Abonne;
import entities.Document;
import entities.Mediatheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Dialogue {
    private PrintWriter writer;
    private BufferedReader reader;
    private Mediatheque m;

    public Dialogue(Socket socket, Mediatheque m) throws IOException {
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.m = m;
    }

    public void envoyerInfo(String msg){
        writer.println("##" + msg);
    }

    private String lireReponse() throws IOException {
        String line = reader.readLine();
        if (line == null || line.equalsIgnoreCase("q")) {
            System.out.println("Le client a arrete la session");
            return null;
        }
        return line.trim();
    }

    public Abonne demanderAbonne() throws IOException{
        Abonne abonne = null;
        while (abonne == null) {
            writer.println("Veuillez entrer votre numero d'abonne : ");
            System.out.println("Demande du numero d'abonne au client");
            String line = lireReponse();
            if (line == null) return null;
            try {
                int num = Integer.parseInt(line.trim());
                abonne = m.getAbonne(num);
                if (abonne == null) {
                    writer.println("Erreur : Numero inconnu.");
                }
            } catch (NumberFormatException e) {
                writer.println("Erreur : Vous devez saisir un nombre entier.");
            }
        }
        return abonne;
    }

    public Document demanderDocument() throws IOException{
        Document doc = null;
        while (doc == null) {
            writer.println("Entrez l'identifiant du document :");
            System.out.println("Demande de l'identifiant du document au client");
            String line = lireReponse();
            if (line == null) return null;
            try {
                int id = Integer.parseInt(line.trim());
                doc = m.getDocument(id);
                if (doc == null) {
                    writer.println("Erreur : Document inexistant.");
                }
            } catch (NumberFormatException e) {
                writer.println("Erreur : L'ID doit etre un nombre.");
            }
        }
        return doc;
    }
}
