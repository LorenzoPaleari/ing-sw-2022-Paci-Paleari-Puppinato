package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ViewUtilities.GameInfo;
import it.polimi.ingsw.client.ViewUtilities.IPValidator;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.model.enumerations.PawnColor.lookup;

public class CLIView implements View{
    private GameInfo gameInfo;
    private Scanner scanner;
    private ServerHandler serverHandler;


    public CLIView(ServerHandler serverHandler){
        scanner = new Scanner(System.in);
        this.serverHandler = serverHandler;
    }

    @Override
    public void start() {
        String serverIP;
        boolean valid;

        do {
            System.out.print("Enter the server IP [Press enter for default IP]: ");
            serverIP = scanner.nextLine();

            if (serverIP.equals("")){
                valid = true;
                serverIP = IPValidator.getDefaultIP();
                System.out.println(" > Default server IP has been chosen, " + serverIP);
            } else {
                if (!IPValidator.isCorrectIP(serverIP)){
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
    public void gameSetUp(List<String[]> lobbies) {
        boolean valid;
        String response;

        System.out.print("Vuoi cominciare una nuova partita? [Yes/No]:");
        do {
            valid = true;
            response = scanner.nextLine();
            response = response.toUpperCase();
            if (!(response.equals("YES") || response.equals("NO"))){
                valid = false;
                System.out.println("Scelta non valida, prova di nuovo:");
            }
        } while (!valid);

        if (response.equals("YES"))
            serverHandler.setGame(true, -1);
        else if (lobbies.size() == 0){
            System.out.println("Non c'è nessuna Lobby, ne verrà creata una nuova");
            serverHandler.setGame(true, -1);
        }
        else{
            lobbySelection(lobbies);
        }
    }

    @Override
    public void refreshLobbies(List<String[]> lobbies) {
        lobbySelection(lobbies);
    }

    private void lobbySelection(List<String[]> lobbies) {
        boolean valid;
        printLobbies(lobbies);

        do{
            valid = true;
            if (!checkForValidLobby(lobbies)){
                System.out.print("Tutte le lobby sono piene o in creazione. Premi invio per refreshare oppure NewGame per iniziare una nuova partita:");
                String lobby = scanner.nextLine();
                if (lobby == "")
                    serverHandler.refreshLobbies();
                else if (lobby.toLowerCase().matches("newgame"))
                    serverHandler.setGame(true, -1);
                else {
                    System.out.print("Il comando inserito non è valido, riprova.");
                    valid = false;
                }
            } else {
                System.out.print("Inserisci il numero della lobby nella quale vuoi entrare oppure premi invio per refreshare le lobby:");
                String lobby = scanner.nextLine();
                if (lobby == "")
                    serverHandler.refreshLobbies();
                else if (lobby.matches("[0-9]+")) {
                    int numLobby = Integer.parseInt(lobby);
                    if (numLobby > lobbies.size() || numLobby < 0) {
                        System.out.print("Il numero inserito non è valido, riprova.");
                        valid = false;
                    } else if (lobbies.get(numLobby - 1)[0].equals("FULL")) {
                        System.out.print("Il numero inserito corrisponde ad una Lobby piena, riprova.");
                        valid = false;
                    } else if (lobbies.get(numLobby - 1)[0].equals("Starting... ")) {
                        System.out.print("Il numero inserito corrisponde ad una lobby in creazione, prova a refreshare.");
                        valid = false;
                    } else
                        serverHandler.setGame(false, numLobby - 1);
                }
            }
        } while (!valid);
    }

    private void printLobbies(List<String[]> lobbies) {
        for(int i = 0; i < lobbies.size(); i++) {
            if (!(lobbies.get(i)[0].equals("Starting... ") || lobbies.get(i)[0].equals("FULL"))) {
                System.out.print("Lobby-" + (i + 1) + " : ");
                for (int j = 0; j < lobbies.get(i).length; j++)
                    if (j == 0)
                        System.out.print(lobbies.get(i)[j]);
                    else
                        System.out.println(", "+ lobbies.get(i)[j]);
            }

            System.out.println();
        }
    }

    private boolean checkForValidLobby(List<String[]> lobbies){
        for (String[] s : lobbies)
            if (!(s[0].equals("Starting... ") || s[0].equals(("FULL"))))
                return true;

        return false;
    }

    @Override
    public void fullLobby(List<String[]> lobbies){
        System.out.println("La lobby si è riempita, prova di nuovo");
        gameSetUp(lobbies);
    }

    @Override
    public void initialSetUp(boolean firstPlayer) {
        boolean expert= false;
        boolean valid1 = false;
        boolean valid2;
        int numPlayer;
        do {
            System.out.print("Inserire il numero di giocatori [2..3]:  ");
            numPlayer = Integer.parseInt(scanner.nextLine());
            if (numPlayer<2 || numPlayer>3)
                System.out.print("Hai sbagliato a inserire un numero... ");
            else
                valid1=true;

        } while (!valid1);
        do {
            valid2 = true;
            System.out.print("Vuoi giocare in expert mode? choose between yes or no: ");
            String expertString = scanner.nextLine();
            if ((expertString.equalsIgnoreCase("YES"))) {
                expert = true;
            } else if (!(expertString.equalsIgnoreCase("NO"))) {
                System.out.print("Errore, hai sbagliato davvero nello scrivere 3 lettere... ");
                valid2 = false;
            }
        }while(!valid2);

        serverHandler.initialSetUp(numPlayer, expert);
    }

    @Override
    public void playerSetUp(boolean requestAgain) {
        if (requestAgain) {
            System.out.print("Questo nickname è già stato preso, scegline un altro:  ");
        } else {
            System.out.print("Scegli un nickname:  ");
        }

        String nickname = scanner.nextLine();
        serverHandler.setPlayerInfo(nickname);
    }

    @Override
    public void colorSetUp(List<TowerColor> tower) {
        boolean valid=false;
        TowerColor color = null;
        if(tower.size() == 1){
            color = tower.get(0);
            System.out.print("Il tuo colore sarà " + tower.get(0));
        }
        else {
            do {
                System.out.print("Scegli un colore:  ");
                for (TowerColor t : tower)
                    System.out.print(t.toString() + " ");
                String colorString = scanner.nextLine();
                colorString = colorString.toUpperCase();
                if (tower.contains(TowerColor.getColor(colorString))) {
                    valid = true;
                    color = TowerColor.getColor(colorString);
                } else {
                    System.out.print("Hai sbagliato a inserire il colore...\n ");
                }

            } while (!valid);
        }

        serverHandler.setPlayerColor(color);
    }

    @Override
    public void printGameBoard(GameInfo gameInfo){
        this.gameInfo = gameInfo;
        System.out.println("Sto funzionando");
        choseAction();
    }

    @Override
    public void choseAction(){
        System.out.print("Insert a command: ");
        int code = Integer.parseInt(scanner.nextLine());
        switch (code){
            case 1:
                System.out.print("Insert the number of the cloud you want to chose: ");
                int cloudPosition=Integer.parseInt(scanner.nextLine());
                serverHandler.cloudChosenRequest(cloudPosition);
                break;
            case 2:
                System.out.print("Insert the number of movements of Mother Nature: ");
                int endPosition=Integer.parseInt(scanner.nextLine());
                serverHandler.moveMotherNatureRequest(endPosition);
                break;
            case 3:
                System.out.print("Insert the number of the island you want to chose: ");
                int islandPosition=Integer.parseInt(scanner.nextLine());
                System.out.print("Insert the color of the student you want to move: ");
                String colorString= scanner.nextLine();
                PawnColor color=lookup(colorString);
                serverHandler.moveStudentToIslandRequest(islandPosition, color);
                break;
            case 4:
                System.out.print("Insert the color of the student you want to move: ");
                String colorString2= scanner.nextLine();
                PawnColor color2=lookup(colorString2);
                serverHandler.moveToDiningRoomRequest(color2);
                break;
            case 5:
                System.out.print("Insert the position of the assistant card you want to use: ");
                int position=Integer.parseInt(scanner.nextLine());
                serverHandler.useAssistantRequest(position);
                break;

        }
    }

    @Override
    public void printError(ClientException exception){
        if (exception.getErrorType().equals(ErrorType.NOT_ENOUGH_MONEY))
            System.out.println(exception.getErrorType().getErrorText() + exception.getPrice());
        else if (exception.getErrorType().equals(ErrorType.MAX_STUDENT_REACHED))
            System.out.println(exception.getErrorType().getErrorText() + exception.getPawnColor());
        else
            System.out.println(exception.getErrorType().getErrorText());
        choseAction();
    }

    @Override
    public void printWinner(String winner1, String winner2, String nickname) {
        if(winner2==null){
            if(winner1.equals(nickname))
                System.out.println("You have won");
            else
                System.out.println(winner1+" has won");
        }
        else
            if(winner1.equals(nickname))
                System.out.println("It's a draw! You and " +winner2+ " have won the game");
            else if(winner2.equals(nickname))
                System.out.println("It's a draw! You and " +winner1+ " have won the game");
            else
                System.out.println("It's a draw! "+ winner1 +" and " +winner2+ " have won the game");
    }

    @Override
    public void printInterrupt(String nickname, String player) {
        System.out.println("The game was interrupted, " + nickname + " disconnected from the server." );
        serverHandler.endConnection();
    }


}
