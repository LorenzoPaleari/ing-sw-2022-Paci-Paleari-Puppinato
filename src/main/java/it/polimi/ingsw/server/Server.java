package it.polimi.ingsw.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static final int PORT = 8080;
    //public static Controller controller;
    public static Server server;
    public static ServerSocket serverSocket;
    //private static VirtualView virtualView;
    private static LobbyHandler lobbyHandler;
    private static int maxPlayer = 3;
    private static boolean active = false;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch(Exception e){
            System.out.println("Server is not started");
        }
        System.out.println("Server successfully started");
        active = true;
        server= new Server();
        lobbyHandler = new LobbyHandler();
        //controller = new Controller();
        //virtualView= new VirtualView(controller);
        //controller.setVirtualView(virtualView);
        server.acceptPlayer();
    }

    public void acceptPlayer() {
        int numPlayer=0;
        boolean firstPlayer= true;
        while(active){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted: " + socket.getRemoteSocketAddress());
                numPlayer++;
                ClientHandler clientHandler=new ClientHandler(socket, "TemporaryName"+numPlayer, lobbyHandler);
                //virtualView.addClientHandler(clientHandler);
                clientHandler.start();
                //firstPlayer=false;

                /*synchronized (lobbyHandler){
                    while (Controller.getGame().getNumPlayer() == -1)
                        lobbyHandler.wait();
                }*/
                /*for (Controller c : lobbyHandler.getControllers()) {
                    synchronized (c){
                        while (c.getGame().getNumPlayer() == -1) {
                            c.wait();
                        }
                    }
                }*/
            }
            catch(IOException e){
                System.out.println("Error with connection");
            }
        }
    }

    public static void setMaxPlayer(int maxPlayer) {
        Server.maxPlayer = maxPlayer;
    }
}
