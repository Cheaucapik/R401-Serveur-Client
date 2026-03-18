package documents;
import entities.*;

public class Livre implements Document {
    private String titre;
    private int nbPages;
    private int id;

    public Livre(int id, String titre, int nbPages) {
        this.titre = titre;
        this.nbPages = nbPages;
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
