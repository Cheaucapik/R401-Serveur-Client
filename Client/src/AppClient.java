import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppClient {
    private static int PORT;
    private static String HOST = "localhost";

    public static void main(String[] args) {
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in)); //ce qu'on tape

        if(args.length < 1){
            System.out.println("Usage: java AppClient <port>");
        }

        PORT = Integer.parseInt(args[0]);

        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);
            BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( ))); //ce que le serveur envoie
            PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true); //ce qu'on envoie au serveur

            System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());

            String reponse;
            while(sin.readLine() != null) {
                System.out.println(sin.readLine()); //question
                System.out.print("->"); //flèche pour répondre
                reponse = clavier.readLine(); //réponse entrée au clavier
                sout.println(reponse); //envoi de la réponse au serveur/service concerné
            }
            socket.close();
        }
        catch (IOException e) { System.err.println("Fin du service"); }
        try { if (socket != null) socket.close(); }
        catch (IOException e2) { ; }
    }

    private static boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
