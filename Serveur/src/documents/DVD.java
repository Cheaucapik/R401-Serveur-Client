package documents;
import entities.*;

import java.time.LocalDate;
import java.time.Period;

public class DVD extends ADocument {
    private boolean adulte;

    public DVD(int id, String titre, boolean adulte) {
        super(id, titre);
        this.adulte = adulte;
    }

    @Override
    public void reservation(Abonne ab) throws ReservationException {
        if (!isAdulte(ab)) {
            throw new ReservationException("Vous n'avez pas l'âge pour emprunter le DVD " + getTitre());
        }
        super.reservation(ab);
    }

    @Override
    public void emprunt(Abonne ab) throws EmpruntException {
        if (!isAdulte(ab)) {
            throw new ReservationException("Vous n'avez pas l'âge pour emprunter le DVD " + getTitre());
        }
        super.emprunt(ab);
    }

    public boolean isAdulte(Abonne ab) {
        if (this.adulte) {
            LocalDate dateAb = ab.getDateNaissance();
            LocalDate maintenant = LocalDate.now();
            return Period.between(dateAb, maintenant).getYears() >= 16;
        }
        return true;
    }
}
