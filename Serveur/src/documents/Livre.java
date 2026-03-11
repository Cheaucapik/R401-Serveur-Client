package documents;
import entities.*;

public class Livre implements Document {
    private String titre;
    private int nbPages;

    public Livre(String titre, int nbPages) {
        this.titre = titre;
        this.nbPages = nbPages;
    }

    @Override
    public String idDoc() {
        return "";
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
