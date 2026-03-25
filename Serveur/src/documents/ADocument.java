package documents;

import entities.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ADocument implements Document {
    private int id;
    private String titre;
    private Abonne reservePar = null;
    private Abonne empruntePar = null;
    private LocalDateTime dateReservFin = null;
    private LocalDateTime dateEmprunteFin = null;

    public ADocument(int id, String titre) {
        this.id = id;
        this.titre = titre;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public String getTitre(){
        return titre;
    }

    @Override
    public void reservation(Abonne ab) throws ReservationException {
        synchronized (this) {
            if(empruntePar != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                throw new ReservationException("Le " + this.getClass().getSimpleName() + " " + titre + " est deja emprunte par un autre abonne jusqu'au " + dateEmprunteFin.format(formatter));
            }
            if(reservePar != null && dateReservFin != null && dateReservFin.isAfter(LocalDateTime.now())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                throw new ReservationException("Le " + this.getClass().getSimpleName() + " " + titre + " est deja reserve par un autre abonne jusqu'a " + dateReservFin.format(formatter));
            }

            reservePar = ab;
            dateReservFin = LocalDateTime.now().plusHours(2);
        }
    }

    @Override
    public void emprunt(Abonne ab) throws EmpruntException {
        synchronized (this) {
            if(empruntePar != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                throw new EmpruntException("Le " + this.getClass().getSimpleName() + " " + titre + " est deja emprunte par un autre abonne jusqu'au " + dateEmprunteFin.format(formatter));
            }
            if(reservePar != null && dateReservFin != null && dateReservFin.isAfter(LocalDateTime.now())) {
                if(!reservePar.equals(ab)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    throw new EmpruntException("Le " + this.getClass().getSimpleName() + " " + titre + " est deja reserve par un autre abonne jusqu'a " + dateReservFin.format(formatter));
                }
            }
            empruntePar = ab;
            dateEmprunteFin = LocalDateTime.now().plusMonths(1);
            this.reservePar = null;
            this.dateReservFin = null;
        }
    }

    @Override
    public void retour() throws RetourException{
        synchronized (this) {
            System.out.println("Document ID: " + this.id);
            System.out.println("Emprunteur actuel: " + this.empruntePar);
            if(empruntePar == null) {
                throw new RetourException("Le " + this.getClass().getSimpleName() + " " + titre + " n'est emprunte par personne, il ne peut pas etre retourne");
            }
            empruntePar = null;
            dateEmprunteFin = null;
        }
    }

}
