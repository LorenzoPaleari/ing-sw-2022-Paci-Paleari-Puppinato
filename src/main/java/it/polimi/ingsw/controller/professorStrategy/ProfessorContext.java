package it.polimi.ingsw.controller.professorStrategy;

import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

public class ProfessorContext extends Context {
    private ProfessorStrategy strategy;

    public ProfessorContext(ProfessorStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void changeContext(ProfessorStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void professorControl(Game game, Player player, PawnColor color){
        strategy.checkProfessor(game, player, color);
    }
}
