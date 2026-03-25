package serveur;
import entities.Mediatheque;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur implements Runnable {
    private int numPort;
    private ServerSocket serverSocket;
    private Class<? extends Runnable> serviceClass;
    private Mediatheque mediatheque;


    public Serveur(Class<? extends Runnable> serviceClass, int port, Mediatheque mediatheque) {
        this.numPort = port;
        this.serviceClass = serviceClass;
        this.mediatheque = mediatheque;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(numPort);
            System.out.println("Lancement du serveur au port " + numPort);
            while (true) {
                Socket client = this.serverSocket.accept();
                new Thread(serviceClass.getDeclaredConstructor(Socket.class, Mediatheque.class).newInstance(client, mediatheque)).start();
            }
        } catch (IOException | NoSuchMethodException e) {
            System.out.println(e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void finalize() throws Throwable{
        try{this.serverSocket.close();}
        catch (IOException e){}
    }
}
