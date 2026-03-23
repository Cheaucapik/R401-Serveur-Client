package entities;

public interface Document {
    void reservation (Abonne ab) throws ReservationException;
    void emprunt(Abonne ab) throws EmpruntException;
    void retour() throws RetourException;
    int getId();
    String getTitre();
}
