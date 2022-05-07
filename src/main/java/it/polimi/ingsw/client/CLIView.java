package it.polimi.ingsw.client;

import it.polimi.ingsw.client.viewUtilities.GameInfo;
import it.polimi.ingsw.client.viewUtilities.IPValidator;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.model.enumerations.PawnColor.getColor;
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
    public void gameSetUp() {
        boolean valid;
        String response;

        System.out.print("Vuoi cominciare una nuova partita? [Yes/No]:");
        do {
            valid = true;
            response = scanner.nextLine().toUpperCase();
            if (!(response.equals("YES") || response.equals("NO"))){
                valid = false;
                System.out.print("Scelta non valida, prova di nuovo: ");
            }
        } while (!valid);

        if (response.equals("YES"))
            serverHandler.setGame(true, -1);
        else {
            serverHandler.refreshLobbies();
        }
    }

    @Override
    public void refreshLobbies(List<String[]> lobbies, boolean firstLobby) {
        if (firstLobby){
            System.out.println("Non c'è nessuna Lobby, ne verrà creata una nuova");
            serverHandler.setGame(true, -1);
        } else
            lobbySelection(lobbies);
    }

    private void lobbySelection(List<String[]> lobbies) {
        boolean valid;
        int lobbySize = printLobbies(lobbies);

        do{
            valid = true;
            if (!checkForValidLobby(lobbies))
                System.out.print("Tutte le lobby sono in creazione. Premi invio per refresh oppure \"NewGame\" per iniziare una nuova partita: ");
            else
                System.out.print("Inserisci il numero della lobby nella quale vuoi entrare, premi invio per il refresh delle lobby o \"NewGame\" per iniziare una partita: ");
            String lobby = scanner.nextLine();
            if (lobby.equals(""))
                serverHandler.refreshLobbies();
            else if (lobby.toLowerCase().matches("newgame"))
                serverHandler.setGame(true, -1);
            else if (lobby.matches("\\d+")) {
                int numLobby = Integer.parseInt(lobby);
                if (numLobby > lobbySize || numLobby < 0) {
                    System.out.print("Il numero inserito non è valido, riprova.");
                    valid = false;
                } else
                    serverHandler.setGame(false, lobbyCount(numLobby, lobbies));
            } else {
                System.out.print("Il comando inserito non è valido, riprova: ");
                valid = false;
            }
        } while (!valid);
    }

    private int lobbyCount(int numLobby, List<String[]> lobbies){
        int j = 0;
        int i;
        for (i = 0; j!=numLobby; i++)
            if (!lobbies.get(i)[0].equals("Starting... "))
                j++;

        return i;
    }

    private int printLobbies(List<String[]> lobbies) {
        int k = 0;
        for (String[] lobby : lobbies) {
            if (!lobby[0].equals("Starting... ")) {
                k++;
                System.out.print("Lobby-" + (k) + " : ");
                for (int j = 0; j < lobby.length - 2; j++)
                    if (j == 0) {
                        System.out.print(lobby[j]);
                    } else {
                        System.out.print(", " + lobby[j]);
                    }
                String expert = "Yes";
                if (lobby[lobby.length - 1].equals("false"))
                    expert = "No";
                System.out.print("\t\t NumPlayers: "+lobby[lobby.length - 2]+" - ExpertMode: " + expert);
                System.out.println();
            }
        }
        return k;
    }

    private boolean checkForValidLobby(List<String[]> lobbies){
        for (String[] s : lobbies)
            if (!(s[0].equals("Starting... ")))
                return true;

        return false;
    }

    @Override
    public void fullLobby(List<String[]> lobbies){
        System.out.println("La lobby si è riempita, prova di nuovo");
        gameSetUp();
    }

    @Override
    public void initialSetUp() {
        boolean expert= false;
        boolean valid1 = false;
        boolean valid2;
        int numPlayer=0;
        do {
            System.out.print("Inserire il numero di giocatori [2..3]:  ");
            String str=scanner.nextLine();
            if(str.matches("\\d+")) {
                numPlayer = Integer.parseInt(str);
                if (numPlayer < 2 || numPlayer > 3)
                    System.out.print("Hai sbagliato a inserire un numero... ");
                else
                    valid1 = true;
            }
            else
                System.out.print("Hai sbagliato a inserire un numero... ");

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
            System.out.println("Il tuo colore sarà " + tower.get(0));
        }
        else {
            do {
                System.out.print("Scegli un colore:  ");
                for (TowerColor t : tower)
                    System.out.print(t.toString() + " ");
                String colorString = scanner.nextLine().toUpperCase();
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
        System.out.print("Sto funzionando");
        if (gameInfo.getCurrentPlayer().equals(gameInfo.getFrontPlayer()))
            choseAction();
    }

    @Override
    public void choseAction(){
        System.out.println();
        System.out.print("Insert a command: ");
        boolean valid=false;

        int code = Integer.parseInt(scanner.nextLine());
        switch (code) {
            case 1:
                do {
                    System.out.print("Insert the number of the cloud you want to chose: ");
                    String strCloud = scanner.nextLine();
                    if (strCloud.matches("\\d+")) {
                        int cloudPosition = Integer.parseInt(strCloud);
                        if(cloudPosition<0 || cloudPosition>gameInfo.getNumPlayer())
                            System.out.println("Invalid Cloud Number \n");
                        else {
                            serverHandler.cloudChosenRequest(cloudPosition);
                            valid = true;
                        }
                    } else
                        System.out.println("Invalid Choice  \n");
                }while(!valid);
                break;
            case 2:
                do {
                    System.out.print("Insert the final position of Mother Nature: ");
                    String strPosition = scanner.nextLine();
                    if (strPosition.matches("\\d+")) {
                        int endPosition = Integer.parseInt(strPosition);
                        serverHandler.moveMotherNatureRequest(endPosition);
                        valid=true;
                    } else
                        System.out.println("Invalid Choice \n");
                }while(!valid);
                break;
            case 3:
                do {
                    System.out.print("Insert the number of the island you want to chose: ");
                    String strNumIsland = scanner.nextLine();
                    if (strNumIsland.matches("\\d+")) {
                        int islandPosition = Integer.parseInt(strNumIsland);
                        System.out.print("Insert the color of the student you want to move: ");
                        String colorString = scanner.nextLine();
                        PawnColor color = lookup(colorString);
                        if (color == null)
                            System.out.print("This is not a valid color \n");
                        else if (gameInfo.getEntranceStudents(gameInfo.getCurrentPlayer())[color.getIndex()] == 0) {
                            System.out.print("You don't have this student \n");
                        } else {
                            serverHandler.moveStudentToIslandRequest(islandPosition, color);
                            valid = true;
                        }
                    } else
                        System.out.println("Invalid Choice \n");
                }while(!valid);
                break;
            case 4:
                do {
                    System.out.print("Insert the color of the student you want to move: ");
                    String colorString2 = scanner.nextLine();
                    PawnColor color2 = lookup(colorString2);
                    if (color2 == null)
                        System.out.print("This is not a valid color \n");
                    else if (gameInfo.getEntranceStudents(gameInfo.getCurrentPlayer())[color2.getIndex()] == 0) {
                        System.out.print("You don't have this student \n");
                    } else {
                        serverHandler.moveToDiningRoomRequest(color2);
                        valid=true;
                    }
                }while(!valid);
                break;
            case 5:
                do {
                    System.out.print("Insert the position of the assistant card you want to use: ");
                    String strAssistant = scanner.nextLine();
                    if (strAssistant.matches("\\d+")) {
                        int position = Integer.parseInt(strAssistant);
                        serverHandler.useAssistantRequest(position);
                        valid=true;
                    } else
                        System.out.println("Invalid Choice \n");
                }while(!valid);
                break;
            case 6: // solo temporaneo, non usare
                do {
                    System.out.print("Insert the position of the character card you want to use: ");
                    String strCharacter = scanner.nextLine();
                    if (strCharacter.matches("\\d+")) {
                        int position = Integer.parseInt(strCharacter);
                        valid=true;
                    } else
                        System.out.println("Invalid Choice \n");
                }while(!valid);
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
    public void printInterrupt(String nickname) {
        if (nickname.equals("")){
            System.out.println("The game was interrupted just before your entrance, please choose another Lobby.");
            serverHandler.refreshLobbies();
        }else{
            System.out.println("The game was interrupted, " + nickname + " disconnected from the server.");
            serverHandler.endConnection();
        }
    }


}
