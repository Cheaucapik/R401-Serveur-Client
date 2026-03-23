package documents;
import entities.*;

public class Livre extends ADocument {
    private int nbPages;

    public Livre(int id, String titre, int nbPages) {
        super(id, titre);
        this.nbPages = nbPages;
    }
}
