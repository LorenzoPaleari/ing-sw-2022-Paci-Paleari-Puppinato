package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.player.Player;

public class TurnController {

    public boolean checkPermission(Turn turn, Player player, PlayerState state){
        boolean value = true;

        if (!checkCorrectTurn(turn, player)){
            System.out.println("Not Your Turn");
            value = false;
        }

        if(!checkCorrectAction(player, state)){
            if (state.equals(PlayerState.PLANNING))
                System.out.println("It's Action Phase, you need to move a Student");
            else
                System.out.println("It's Planning Phase, you need to choose an Assistant");
            value = false;
        }

        return value;
    }

    public boolean canMove(Turn turn){
        boolean value = true;
        if (turn.getRemainingMovements() == 0){
            value = false;
            System.out.println("You have already moved all the student, please move Mother Nature");
        } else {
           turn.updateRemainingMovements();
        }

        return value;
    }

    public boolean checkCorrectTurn(Turn turn, Player player){
        boolean value = false;

        if(turn.getCurrentPlayer().equals(player))
            value = true;

        return value;
    }

    public boolean checkCorrectAction(Player player, PlayerState state){
        boolean value = false;

        if (player.getState().equals(state))
            value = true;

        return value;
    }

}
