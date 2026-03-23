package entities;

import java.time.LocalDate;

public class Abonne {
    private int numero;
    private String nom;
    private LocalDate DateNaissance;

    public Abonne(int numero, String nom, LocalDate DateNaissance) {
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

    public LocalDate getDateNaissance() {
        return DateNaissance;
    }
}
