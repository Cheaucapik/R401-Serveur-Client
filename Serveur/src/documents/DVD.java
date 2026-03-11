package documents;
import entities.*;

public class DVD implements Document {
    private String titre;
    private boolean adulte;

    public DVD(String titre, boolean adulte) {
        this.titre = titre;
        this.adulte = adulte;
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
