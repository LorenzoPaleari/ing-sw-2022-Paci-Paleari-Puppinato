package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        View view;
        boolean incorrect;
        do {
            System.out.print("Choose the interface you want to use [CLI/GUI]: ");
            String preferredInterface = scanner.nextLine();
            if ((preferredInterface.equalsIgnoreCase("CLI"))) {
                view = new CLIView();
                incorrect = false;
            } else if ((preferredInterface.equalsIgnoreCase("GUI"))) {
                //view = new GuiView();
                incorrect = false;
            } else {
                System.out.println("Invalid choice. Try again.");
                incorrect = true;
            }
        }
        while (incorrect);

        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, Server.PORT);
        try {
            System.out.println("socket = " +  socket);
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream()));
            PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter( socket.getOutputStream())),true);
            for(int i = 0; i < 10; i ++) {
                out.println("ciao " + i);
                String str = in.readLine();
                System.out.println(str);
            }
            out.println("END");
        } finally {
            System.out.println("closing...");
            socket.close();
        }
    }
}
