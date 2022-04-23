package it.polimi.ingsw.server;
import it.polimi.ingsw.controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static final int PORT = 8080;
    public static Controller controller;
    public static Server server;
    public static ServerSocket serverSocket;
    private static VirtualView virtualView;
    private static int maxPlayer = 3;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch(Exception e){
            System.out.println("Server is not started");
        }
        System.out.println("Server successfully started");
        server= new Server();
        controller = new Controller();
        virtualView= new VirtualView(controller);
        server.acceptPlayer();
    }

    public void acceptPlayer() {
        int numPlayer=0;
        boolean firstPlayer= true;
        while(numPlayer < maxPlayer){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted: " + socket.getRemoteSocketAddress());
                numPlayer++;
                ClientHandler clientHandler=new ClientHandler(socket, firstPlayer, virtualView, ""+numPlayer);
                virtualView.addClientHandler(clientHandler);
                clientHandler.start();
                firstPlayer=false;

                synchronized (virtualView){
                    while (Controller.getGame().getNumPlayer() == -1)
                        virtualView.wait();
                }
            }
            catch(IOException e){
                System.out.println("Error with connection");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (true)
            try {
                Socket socket = serverSocket.accept();

                socket.close();
            } catch (IOException e) {

            }
    }

    public static void setMaxPlayer(int maxPlayer) {
        Server.maxPlayer = maxPlayer;
    }
}
