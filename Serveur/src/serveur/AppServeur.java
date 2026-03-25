package serveur;
import entities.Mediatheque;
import services.ServiceReservation;
import services.ServiceEmprunt;
import services.ServiceRetour;
import java.time.LocalDate;

class AppServeur {

    public static void main(String[] args) throws Exception {
        Mediatheque mediatheque = new Mediatheque();

        mediatheque.ajouterAbonne("Océane", LocalDate.of(2006, 11, 7));
        mediatheque.ajouterAbonne("Alice", LocalDate.of(2014, 1, 1));

        mediatheque.ajouterLivre("Vingt Mille Lieues sous les mers", 606);
        mediatheque.ajouterDVD("Scream", true);
        mediatheque.ajouterDVD("T'choupi", false);

        try{
            new Thread(new Serveur(ServiceReservation.class, 2000, mediatheque)).start();
            new Thread(new Serveur(ServiceEmprunt.class, 2001, mediatheque)).start();
            new Thread(new Serveur(ServiceRetour.class, 2002, mediatheque)).start();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
