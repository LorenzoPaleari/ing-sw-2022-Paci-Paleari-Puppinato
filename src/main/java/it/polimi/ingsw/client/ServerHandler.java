package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerHandler {
    private Socket socket;
    public ServerHandler(){
    }
    public void initConnection(String serverIp) throws IOException {
        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, Server.PORT);
        try {
            System.out.println("socket = " +  socket);
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()));
            PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter( socket.getOutputStream())),true);
            String str = in.readLine();
        } finally {
            socket.close();
        }
    }
}
