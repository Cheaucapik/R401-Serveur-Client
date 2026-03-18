package serveur;
import services.ServiceReservation;
import services.ServiceEmprunt;
import services.ServiceRetour;

class AppServeur {

    public static void main(String[] args) throws Exception {
        Mediatheque mediatheque = new Mediatheque();
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
