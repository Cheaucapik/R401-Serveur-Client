package entities;

import java.util.Date;

public class Abonne {
    private int numero;
    private String nom;
    private Date DateNaissance;

    public Abonne(int numero, String nom, Date DateNaissance) {
        this.numero = numero;
        this.nom = nom;
        this.DateNaissance = DateNaissance;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public Date getDateNaissance() {
        return DateNaissance;
    }
}
