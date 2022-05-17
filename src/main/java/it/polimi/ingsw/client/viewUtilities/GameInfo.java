package it.polimi.ingsw.client.viewUtilities;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

import java.io.Serializable;
import java.util.*;

public class GameInfo implements Serializable {
    private String[][] players;
    private int[] playersInfo = new int[2]; //Indexes of the currentTurnPlayer and the frontPlayer
    private int remainingMoves;
    private int[] islandSize;
    private int motherNaturePosition;
    private int numberOfPlayer;
    private int numberOfIsland;

    private Integer[] diningStudents;
    private Integer[] entranceStudents;
    private String[] professors;
    private int[] boardTower;
    private Integer[] assistants;
    private int[] lastUsed;

    private Integer[] islandStudents;
    private int[] islandTowers;

    private CharacterType[] character;
    private int[] characterCost;
    private int[][] characterInfo; //The first 5 are the eventual student, the sixth is the number of NoEntryTiles

    public GameInfo(Game game, String frontPlayer){
        init(game.getNumPlayer(), game.getTable().getNumIsland(), game.isExpertMode());

        for(int i = 0; i < game.getNumPlayer(); i++){
            players[i][0] = game.getPlayers().get(i).getNickname();
            players[i][1] = String.valueOf(game.getPlayers().get(i).getTowerColor());
        }

        playersInfo[0] = playerPosition(game.getRound().getTurn().getCurrentPlayer().getNickname());
        playersInfo[1] = playerPosition(frontPlayer);
        motherNaturePosition = game.getTable().getMotherPosition();

        remainingMoves = game.getRound().getTurn().getRemainingMovements();
        for (int i = 0; i < islandSize.length; i++){
            islandSize[i] = game.getTable().getIsland(i).getWeight();
        }

        List<Integer> tempListEntrance = new ArrayList<>();
        List<Integer> tempListDining = new ArrayList<>();
        List<Integer> tempAssistant = new ArrayList<>();
        int index = 0;
        for(Player p : game.getPlayers()){
            tempListEntrance.addAll(index*5, Arrays.asList(p.getBoard().getEntrance().countAll()));
            tempListDining.addAll(index*5, Arrays.asList(p.getBoard().getDiningRoom().countAll()));
            for(Professor prof : p.getBoard().getProfessorTable().getProfessors()){
                professors[prof.getColor().getIndex()] = p.getNickname();
            }
            boardTower[index] = p.getBoard().getTowerCourt().getTower().size();
            lastUsed[index] = p.getLastUsed().getWeight();
            if (p.getNickname().equals(getFrontPlayer()))
                for (Assistant a : p.getDeck().getAssistant())
                    tempAssistant.add(a.getWeight());

            index++;
        }
        entranceStudents = tempListEntrance.toArray(entranceStudents);
        diningStudents = tempListDining.toArray(diningStudents);
        assistants = tempAssistant.toArray(assistants);

        List<Integer> tempIslandList = new ArrayList<>();
        index = 0;
        for (Island i : game.getTable().getIsland()){
            tempIslandList.addAll(index*5, Arrays.asList(i.countAll()));
            if (i.getIslandTower().size() != 0)
                islandTowers[index] = i.getIslandTower().get(0).getColor().getIndex();
            else
                islandTowers[index] = -1;

            index++;
        }
        islandStudents = tempIslandList.toArray(islandStudents);

        if (game.isExpertMode()){
            for(int i = 0; i < 3; i++) {
                character[i] = game.getTable().getCharacter(i).getType();
                characterCost[i] = game.getTable().getCharacter(i).getPrice();
                characterInfo[i][5] = game.getTable().getCharacter(i).getNumNoEntryTiles();
                for (PawnColor pawnColor : PawnColor.values())
                    characterInfo[i][pawnColor.getIndex()] = game.getTable().getCharacter(i).count(pawnColor);
            }
        }
    }

    private void init(int numPlayer, int numIsland, boolean expertMode){
        players = new String[numPlayer][2];
        numberOfPlayer=numPlayer;
        islandSize = new int[numIsland];
        numberOfIsland=numIsland;

        diningStudents = new Integer[5*numPlayer];
        entranceStudents = new Integer[5*numPlayer];
        boardTower = new int[numPlayer];
        professors = new String[] {"","","","",""};
        lastUsed = new int[3];

        islandStudents = new Integer[numIsland * 5];
        islandTowers = new int [numIsland];

        if (expertMode) {
            character = new CharacterType[3];
            characterCost = new int[3];
            characterInfo = new int[3][6];
        }
    }

    private int playerPosition(String nickname){
        for (int index = 0; index < numberOfPlayer; index++ ){
            if (players[index][0].equals(nickname))
                return index;

            index++;
        }

        return -1;
    }

    public int getNumPlayer(){
        return numberOfPlayer;
    }

    public int getNumIsland(){
        return numberOfIsland;
    }

    public CharacterType getCharacter(int position){
        return character[position];
    }

    public int [] getCharacterInfo(int position){
        return characterInfo [position];
    }

    public String[] getPlayersName(){
        String[] names = new String[players.length];
        for(int i = 0; i < players.length; i++){
            names[i] = players[i][0];
        }
        return names;
    }

    public String[] getPlayersTowerColor(){
        String[] colors = new String[players.length];
        for(int i = 0; i < players.length; i++){
            colors[i] = players[i][1];
        }
        return colors;
    }

    public String getCurrentPlayer(){
        return players[playersInfo[0]][0];
    }

    public String getFrontPlayer(){
        return players[playersInfo[1]][0];
    }

    public int getRemainingMoves(){
        return remainingMoves;
    }

    public int getIslandSize(int numIsland){
        return islandSize[numIsland];
    }

    public Integer[] getEntranceStudents(String nickname){
        return getIntegers(nickname, 1);
    }

    private Integer[] getIntegers(String nickname, int from) {
        Integer[] students = new Integer[5];
        int numPlayer = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i][0].equals(nickname)) numPlayer = i;
        }
        int cont = 0;
        if (from == 1)
            for(int j = numPlayer*5; j < (numPlayer+1)*5; j++){
                students[cont] = entranceStudents[j];
                cont++;
            }
        else {
            for(int j = numPlayer*5; j < (numPlayer+1)*5; j++){
                students[cont] = diningStudents[j];
                cont++;
            }
        }
        return students;
    }

    public int getBoardTower(String nickname){
        int numPlayer = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i][0].equals(nickname)) numPlayer = i;
        }
        return boardTower[numPlayer];
    }

    public int getMotherNaturePosition(){
        return motherNaturePosition;
    }

    public Integer[] getDiningStudents(String nickname){
        return getIntegers(nickname, -1);
    }

    public Integer[] getStudentsOnIsland(int numIsland){
        Integer[] students = new Integer[5];
        int cont = 0;
        for(int j = numIsland*5; j < (numIsland+1)*5; j++){
            students[cont] = diningStudents[j];
            cont++;
        }
        return students;
    }

    public TowerColor getTowersOnIsland(int numIsland){
        if(islandTowers[numIsland] == - 1) return null;
        else return TowerColor.getColor(islandTowers[numIsland]);
    }

    public int[] getProfessors(String nickName) {
        int[] colors = {-1,-1,-1,-1,-1};
        for (int i = 0; i < 5; i++)
            if (professors[i].equals(nickName))
                colors[i] = 1;

        return colors;
    }

    public int[] getLastUsed(String nickName){
        return lastUsed;
    }

    public Integer[] getAssistants(){
        return assistants;
    }
}
