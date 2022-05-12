package it.polimi.ingsw.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static final int PORT = 8080;
    public static Server server;
    public static ServerSocket serverSocket;
    private static LobbyHandler lobbyHandler;
    private static boolean active = false;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch(Exception e){
            System.out.println("Server not started correctly");
            return;
        }
        System.out.println("Server successfully started");
        active = true;
        server= new Server();
        lobbyHandler = new LobbyHandler();
        server.acceptPlayer();
    }

    public void acceptPlayer() {
        int numPlayer=0;
        while(active){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted: " + socket.getRemoteSocketAddress());
                numPlayer++;
                ClientHandler clientHandler=new ClientHandler(socket, "TemporaryName"+numPlayer, lobbyHandler);
                clientHandler.start();
            }
            catch(IOException e){
                System.out.println("Error with connection");
            }
        }
    }
}
