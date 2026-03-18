package documents;
import entities.*;

public class DVD implements Document {
    private String titre;
    private boolean adulte;
    private int id;

    public DVD(int id, String titre, boolean adulte) {
        this.titre = titre;
        this.adulte = adulte;
        this.id = id;
    }

    @Override
    public int idDoc() {
        return id;
    }

    @Override
    public void reservation(Abonne ab) throws ReservationException {

    }

    @Override
    public void emprunt(Abonne ab) throws EmpruntException {

    }

    @Override
    public void retour() throws RetourException {

    }
}
