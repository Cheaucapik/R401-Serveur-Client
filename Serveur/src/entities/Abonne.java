package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Abonne {
    private int numero;
    private String nom;
    private LocalDate DateNaissance;
    private boolean banni = false;
    private LocalDateTime dateBanFin = null;

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

    public boolean isBanni() {
        if (!banni) return false;
        if (dateBanFin != null && LocalDateTime.now().isAfter(dateBanFin)) {
            banni = false;
            dateBanFin = null;
            return false;
        }
        return true;
    }

    public void setBanni() {
        this.banni = true;
        //SUJET: this.dateBanFin = LocalDateTime.now().plusMonths(1);
        //TEST(1min)
        this.dateBanFin = LocalDateTime.now().plusMinutes(1);
    }

    public LocalDateTime getDateBanFin() {
        return dateBanFin;
    }
}
