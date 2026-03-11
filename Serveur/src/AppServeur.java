import services.reservation.ServiceReservation;

class AppServeur {
    private static int PORT;

    public static void main(String[] args) {
        new Thread(new Serveur(ServiceReservation.class, PORT)).start();
    }
}
