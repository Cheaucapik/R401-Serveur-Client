package entities;

import documents.DVD;
import documents.Livre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mediatheque {
    private List<Document> documents;
    private List<Abonne> abonnes;
    int abCpt = 0;
    int docCpt = 0;

    public Mediatheque() {
        documents = new ArrayList<Document>();
        abonnes = new ArrayList<Abonne>();
    }

    public synchronized void ajouterLivre(String titre, int pages) {
        docCpt++;
        documents.add(new Livre(docCpt, titre, pages));
    }

    public synchronized void ajouterDVD(String titre, boolean adulte) {
        docCpt++;
        documents.add(new DVD(docCpt, titre, adulte));
    }

    public synchronized void ajouterAbonne(String nom, LocalDate dateNaissance) {
        abCpt++;
        abonnes.add(new Abonne(abCpt, nom, dateNaissance));
    }

    public Abonne getAbonne(int abCpt) {
        for (Abonne a : abonnes) {
            if(a.getNumero() == abCpt) {
                return a;
            }
        }
        return null;
    }

    public Document getDocument(int idDoc) {
        for (Document d : documents) {
            if(d.getId() == idDoc){
                return d;
            }
        }
        return null;
    }
}
