package it.polimi.ingsw.client;

import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;
import java.util.Scanner;

public class CLIView implements View{
    private Scanner scanner;
    private ServerHandler serverHandler;

    public CLIView(ServerHandler serverHandler){
        scanner = new Scanner(System.in);
        this.serverHandler = serverHandler;
    }

    @Override
    public void start() {
        String serverIP;
        boolean valid = false;

        do {
            System.out.print("Enter the server IP [Press enter for default IP]: ");
            serverIP = scanner.nextLine();

            if (serverIP.equals("\n")){
                valid = true;
                serverIP = "127.0.0.1";
                System.out.println(" > Default server IP has been chosen, " + serverIP);
            } else {
                if (!isCorrectIP(serverIP)){
                    valid = false;
                    System.out.println(" > Server IP not valid. Please try again.");
                } else {
                    valid = true;
                }
            }

        } while (!valid);

        serverHandler.initConnection(serverIP);
    }

    @Override
    public void initialSetUp(boolean firstPlayer) {
        System.out.print("Inserire il numero di giocatori [2..3]:  ");

        System.out.print("Vuoi giocare in expert mode?");

        System.out.print("Nickname");

        System.out.print("TowerColor");
    }

    private boolean isCorrectIP(String serverIP){
        for (int i = 0; i < serverIP.length(); i++)
            if (!((serverIP.charAt(i) <= '9' && serverIP.charAt(i) >= '0') || serverIP.charAt(i) == '.'))
                return false;

        return true;
    }
}
