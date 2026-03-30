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

    private final Object lockDocs = new Object();
    private final Object lockAbs = new Object();

    public Mediatheque() {
        documents = new ArrayList<Document>();
        abonnes = new ArrayList<Abonne>();
    }

    public void ajouterLivre(String titre, int pages) {
        synchronized (lockDocs) {
            docCpt++;
            documents.add(new Livre(docCpt, titre, pages));
        }
    }

    public void ajouterDVD(String titre, boolean adulte) {
        synchronized (lockDocs) {
            docCpt++;
            documents.add(new DVD(docCpt, titre, adulte));
        }
    }

    public void ajouterAbonne(String nom, LocalDate dateNaissance) {
        synchronized (lockAbs) {
            abCpt++;
            abonnes.add(new Abonne(abCpt, nom, dateNaissance));
        }
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
