package serveur;

class AppServeur {
    private static int PORT;
    private static String service_class;

    public static void main(String[] args) throws Exception {

        if(args.length < 2) {
            System.out.println("Usage: java Serveur.AppServeur <port> <service_class>");
            return;
        }
        PORT = Integer.parseInt(args[0]);
        service_class = args[1];

        try{
            Class<? extends Runnable> service = (Class<? extends Runnable>) Class.forName(service_class);
            new Thread(new Serveur(service, PORT)).start();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
