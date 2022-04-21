package it.polimi.ingsw.client;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.network.messages.InitialInfoSetUp;
import it.polimi.ingsw.network.messages.SetPlayerInfo;

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

            if (serverIP.equals("")){
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
        boolean expert= false;
        boolean valid1 = false;
        boolean valid2 = false;
        int numPlayer;
        do {
            System.out.print("Inserire il numero di giocatori [2..3]:  ");
            numPlayer = Integer.parseInt(scanner.nextLine());
            if (numPlayer<2 | numPlayer>3)
                System.out.print("Hai sbagliato a inserire un numero... ");
            else
                valid1=true;

        } while (!valid1);
        do {
            System.out.print("Vuoi giocare in expert mode? choose between yes or no: ");
            String expertString = scanner.nextLine();
            if ((expertString.equalsIgnoreCase("YES"))) {
                expert = true;
                valid2 = true;
            } else if ((expertString.equalsIgnoreCase("NO"))){
                expert = false;
                valid2 = true;
            }
            else
                System.out.print("Errore, hai sbagliato davvero nello scrivere 3 lettere... ");
        }while(!valid2);

        serverHandler.send(new InitialInfoSetUp(numPlayer, expert));
    }

    @Override
    public void playerSetUp(List<TowerColor> tower) {
        boolean valid=false;
        TowerColor color = null;
        do {
            System.out.print("Scegli un colore:  ");
            for(TowerColor t: tower)
                System.out.print(t.toString()+" ");
            String colorString = scanner.nextLine();
            if (colorString.equalsIgnoreCase("WHITE")) {
                color=TowerColor.WHITE;
                valid = true;
            }
            else if (colorString.equalsIgnoreCase("BLACK")) {
                color=TowerColor.BLACK;
                valid = true;
            }
            else if (colorString.equalsIgnoreCase("GREY")) {
                color=TowerColor.GREY;
                valid = true;
            }
            else
                System.out.print("Hai sbagliato a inserire un colore... ");

        } while (!valid);
        System.out.print("Scegli un nickname:  ");
        String nickname = scanner.nextLine();
        serverHandler.send(new SetPlayerInfo(color, nickname));
    }

    private boolean isCorrectIP(String serverIP){
        for (int i = 0; i < serverIP.length(); i++)
            if (!((serverIP.charAt(i) <= '9' && serverIP.charAt(i) >= '0') || serverIP.charAt(i) == '.'))
                return false;

        return true;
    }
}
