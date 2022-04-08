package it.polimi.ingsw.server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    public static final int PORT = 8080;
    public static Server server;
    public static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch(Exception e){
            System.out.println("There's another server opened");
        }
        System.out.println("Started: " + serverSocket);
        server= new Server();
        server.acceptPlayer();


        serverSocket.close();
    }

    public void acceptPlayer() {
        int numPlayer=0;
        while(numPlayer<3){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted: "+ socket);
                numPlayer++;
                new ClientHandler(socket);
            }
            catch(IOException e){
                System.out.println("Error with connection");
            }

        }
    }
}
