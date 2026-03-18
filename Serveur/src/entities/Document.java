package entities;

public interface Document {
    int idDoc();
    void reservation (Abonne ab) throws ReservationException;
    void emprunt(Abonne ab) throws EmpruntException;
    void retour() throws RetourException;
}
