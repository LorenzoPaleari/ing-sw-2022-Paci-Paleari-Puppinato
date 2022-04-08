package it.polimi.ingsw.server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    public static final int PORT = 8080;
    public static Server server;
    public static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Started: " + serverSocket);
        server= new Server();
        server.acceptPlayer();


        serverSocket.close();
    }

    public void acceptPlayer() throws IOException {
        int numPlayer=0;
        while(numPlayer<3){
            Socket socket = serverSocket.accept();
            System.out.println("Connection accepted: "+ socket);
            numPlayer++;
            new ClientHandler(socket);
        }
    }
}
