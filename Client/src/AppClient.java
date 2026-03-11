import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppClient {
    private static int PORT = 2000;
    private static String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in)); //ce qu'on tape

        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);
            BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( ))); //ce que le serveur envoie
            PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true); //ce qu'on envoie au serveur

            System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());

            String line;
            while(sin.readLine() != null) {
                line = sin.readLine();
                sout.println(line);
                sout.flush();
            }

            line = sin.readLine();
            System.out.println(line);

            System.out.print("->");
            line = clavier.readLine();
            sout.println(line);
            line = sin.readLine();
            System.out.println(line);

            System.out.print("->");
            line = clavier.readLine();
            sout.println(line);

            System.out.println(sin.readLine());

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
