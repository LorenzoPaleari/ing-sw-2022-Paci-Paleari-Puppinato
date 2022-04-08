package it.polimi.ingsw.client;

import java.util.Scanner;

public class CLIView implements View{
    private Scanner scanner;
    private ServerHanlder serverHandler;

    public CLIView(ServerHandler serverHandler){
        scanner = new Scanner(System.in);
        this.serverHandler = serverHandler;
    }

    @Override
    public void start() {
        String serverIP;
        boolean valid;

        do {
            System.out.println("Enter the server IP [Press enter for default IP]: ");
            serverIP = scanner.nextLine();

            if (serverIP.equals("\n")){
                valid = true;
                serverIP = "127.0.0.1";
                System.out.println(" > Default server IP has been chosen, " + serverIP);
            } else if (!isCorrectIP(serverIP)){
                System.out.println(" > Server IP not valid. Please try again.");
            }

        } while (!valid);

        serverHandler.initConnection(serverIP);
    }

    private boolean isCorrectIP(String serverIP){
        for (int i = 0; i < serverIP.length(); i++)
            if (!((serverIP.charAt(i) <= '9' && serverIP.charAt(i) >= '0') || serverIP.charAt(i) == '.'))
                return false;

        return true;
    }
}
