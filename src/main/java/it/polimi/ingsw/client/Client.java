package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ServerHandler serverHandler = new ServerHandler();
        View view;
        boolean incorrect;
        do {
            System.out.print("Choose the interface you want to use [CLI/GUI]: ");
            String preferredInterface = scanner.nextLine();
            if ((preferredInterface.equalsIgnoreCase("CLI"))) {
                view = new CLIView(serverHandler);
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

    }
}
