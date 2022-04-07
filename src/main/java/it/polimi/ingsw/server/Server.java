package it.polimi.ingsw.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    public static void main(String[] args) throws IOException {
                ServerSocket serverSocket = new ServerSocket(4999);
                Socket connection = serverSocket.accept();

                connection.close();
                serverSocket.close();
            }
    }