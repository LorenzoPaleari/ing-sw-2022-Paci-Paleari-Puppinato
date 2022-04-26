package it.polimi.ingsw.client.ViewUtilities;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

import java.io.Serializable;
import java.util.*;
import java.util.List;

public class GameInfo implements Serializable {
    private String[][] players;
    private int[] playersInfo = new int[2]; //Indexes of the currentTurnPlayer and the frontPlayer

    private int remainingMoves;
    private int[] islandSize;
    private int motherNaturePosition;

    private Integer[] diningStudents;
    private Integer[] entranceStudents;
    private String[] professors = new String[5];
    private int[] boardTower;

    private Integer[] islandStudents;
    private int[][] islandTowers;

    private CharacterType[] character;
    private int[] characterCost;
    private int[] characterInfo; //The first 5 are the eventual student, the sixth is the number of NoEntryTiles

    public GameInfo(Game game, String frontPlayer){
        init(game.getNumPlayer(), game.getTable().getNumIsland(), game.isExpertMode());

        for(int i = 0; i < game.getNumPlayer(); i++){
            players[i][0] = game.getPlayers().get(i).getNickname();
            players[i][1] = String.valueOf(game.getPlayers().get(i).getTowerColor());
        }

        playersInfo[0] = playerPosition(game.getRound().getTurn().getCurrentPlayer().getNickname(), game.getPlayers());
        playersInfo[1] = playerPosition(frontPlayer, game.getPlayers());
        motherNaturePosition = game.getTable().getMotherPosition();

        remainingMoves = game.getRound().getTurn().getRemainingMovements();
        for (int i = 0; i < islandSize.length; i++){
            islandSize[i] = game.getTable().getIsland(i).getWeight();
        }

        List<Integer> tempListEntrance = new ArrayList<>();
        List<Integer> tempListDining = new ArrayList<>();
        int index = 0;
        for(Player p : game.getPlayers()){
            tempListEntrance.addAll(index*5, Arrays.asList(p.getBoard().getEntrance().countAll()));
            tempListDining.addAll(index*5, Arrays.asList(p.getBoard().getDiningRoom().countAll()));
            for(Professor prof : p.getBoard().getProfessorTable().getProfessors()){
                professors[prof.getColor().getIndex()] = p.getNickname();
            }
            boardTower[index] = p.getBoard().getTowerCourt().getTower().size();

            index++;
        }
        entranceStudents = tempListEntrance.toArray(entranceStudents);
        diningStudents = tempListDining.toArray(diningStudents);

        List<Integer> tempIslandList = new ArrayList<>();
        index = 0;
        for (Island i : game.getTable().getIsland()){
            tempIslandList.addAll(index*5, Arrays.asList(i.countAll()));
            islandTowers[index][0] = i.getIslandTower().size();
            if (islandTowers[index][0] != 0)
                islandTowers[index][1] = i.getIslandTower().get(0).getColor().getIndex();

            index++;
        }
        islandStudents = tempIslandList.toArray(islandStudents);

        if (game.isExpertMode()){
            for(int i = 0; i < 3; i++) {
                character[i] = game.getTable().getCharacter(i).getType();
                characterCost[i] = game.getTable().getCharacter(i).getPrice();
                characterInfo[5] = game.getTable().getCharacter(i).getNumNoEntryTiles();
                for (PawnColor pawnColor : PawnColor.values())
                    characterInfo[pawnColor.getIndex()] = game.getTable().getCharacter(i).count(pawnColor);
            }
        }
    }

    private void init(int numPlayer, int numIsland, boolean expertMode){
        players = new String[numPlayer][2];

        islandSize = new int[numIsland];

        diningStudents = new Integer[5*numPlayer];
        entranceStudents = new Integer[5*numPlayer];
        boardTower = new int[numPlayer];

        islandStudents = new Integer[numIsland * 5];
        islandTowers = new int [numIsland][2];

        if (expertMode) {
            character = new CharacterType[3];
            characterCost = new int[3];
            characterInfo = new int[6];
        }
    }

    private int playerPosition(String nickname, List<Player> players){
        int index = 0;

        for (Player p : players){
            if (p.getNickname().equals(nickname))
                return index;

            index++;
        }

        return -1;
    }
}
