package it.polimi.ingsw.server;
import it.polimi.ingsw.controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    public static final int PORT = 8080;
    public static Server server;
    public static ServerSocket serverSocket;
    private static VirtualView virtualView;

    public static void main(String[] args) throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch(Exception e){
            System.out.println("Server is not started");
        }
        System.out.println("Server successfully started");
        server= new Server();
        Controller controller = new Controller();
        virtualView= new VirtualView(controller);
        server.acceptPlayer();


    }

    public void acceptPlayer() {
        int numPlayer=0;
        boolean firstPlayer= true;
        while(numPlayer<3){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted: " + socket.getRemoteSocketAddress());
                numPlayer++;
                ClientHandler clientHandler=new ClientHandler(socket, firstPlayer, virtualView);
                virtualView.addClientHandler(clientHandler);
                firstPlayer=false;
            }
            catch(IOException e){
                System.out.println("Error with connection");
            }

        }
    }
}
