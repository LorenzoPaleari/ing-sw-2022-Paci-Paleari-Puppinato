package it.polimi.ingsw.controller.islandController;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

public class IslandControllerStandard implements IslandController {
    public boolean calculateInfluence(Island island, Game game) {
        boolean value = false;
        TowerColor color = null;
        int MoreInfluence = 0;
        int TempInfluence;
        Player playerCandidate = null;
        Player owner = null;

        if (island.isNoEntryTiles())
            return false;

        if (!island.getIslandTower().isEmpty())
            color = island.getIslandTower().get(0).getColor();

        for (Player p : game.getPlayers()){
            TempInfluence = 0;
            for(Professor prof : p.getBoard().getProfessorTable().getProfessors()){
                TempInfluence += island.countStudent(prof.getColor());
            }
            if(!island.getIslandTower().isEmpty() && p.getTowerColor().equals(color)) {
                TempInfluence += island.getWeight();
                owner = p;
            }

            if (TempInfluence > MoreInfluence){
                MoreInfluence = TempInfluence;
                playerCandidate = p;
            } else if(TempInfluence == MoreInfluence) {
                playerCandidate = null;
            }
        }

        if (playerCandidate == null)
            return false;

        if (color == null){
            island.addTower(playerCandidate.getBoard().getTowerCourt().removeTower(1));
            return true;
        }

        if (!playerCandidate.getTowerColor().equals(color)) {
            value = true;

            owner.getBoard().getTowerCourt().addTower(island.removeTower());
            island.addTower(playerCandidate.getBoard().getTowerCourt().removeTower(island.getWeight()));
        }

        return value;
    }

    @Override
    public void setNoColor(PawnColor noColor) {

    }
}
