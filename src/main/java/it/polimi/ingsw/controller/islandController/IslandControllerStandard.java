package it.polimi.ingsw.controller.islandController;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

public class IslandControllerStandard implements IslandController {
    public void calculateInfluence(Island island, Game game, String[] owner, TowerColor color, String[] playerCandidate) {
        int MoreInfluence = 0;
        for (Player p : game.getPlayers()){
            int TempInfluence = 0;
            for(Professor prof : p.getBoard().getProfessorTable().getProfessors()){
                TempInfluence += island.countStudent(prof.getColor());
            }
            if(!island.getIslandTower().isEmpty() && p.getTowerColor().equals(color)) {
                TempInfluence += island.getWeight();
                owner[0] = p.getNickname();
            }

            if (TempInfluence > MoreInfluence){
                MoreInfluence = TempInfluence;
                playerCandidate[0] = p.getNickname();
            }
        }
    }

    @Override
    public void setPlayer(Player player) {

    }

    @Override
    public void setNoColor(PawnColor noColor) {

    }
}
