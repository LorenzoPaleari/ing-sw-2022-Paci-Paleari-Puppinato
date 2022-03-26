package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.board.DiningRoom;
import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.player.Player;

public class TurnController {

    public void checkCharacter(Game game, Player player, int costo, Character character) throws Exception {
        if(game.getRound().getTurn().isUsedCharacter())
            throw new AlreadyUsedCharacterException();

        checkPermission(game.getRound().getTurn(), player, PlayerState.ACTION);

        enoughMoney(player, costo);

        player.removeCoin(costo);
        game.getTable().addCoin(costo);

        if (!character.isUsed()) {
            game.getTable().withdrawCoin();
            character.firstUse();
        }
    }

    public void checkPermission(Turn turn, Player player, PlayerState state) throws Exception {

        try {
            checkCorrectTurn(turn, player);
        } catch (NotYourTurnException e) {
            System.out.println("Not Your Turn");
            throw new Exception();
        }

        try {
            checkCorrectAction(player, state);
        } catch (WrongPhaseException e) {
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }

    public void canMove(Turn turn) throws WrongActionException {
        if (turn.getRemainingMovements() == 0)
            throw new WrongActionException(0);

        turn.updateRemainingMovements();
    }

    public void canMoveMother(Turn turn) throws WrongActionException {
        if (turn.getRemainingMovements() > 0)
            throw new WrongActionException(1);

    }

    public void enoughMoney(Player player, int costo) throws NotEnoughMoneyException {
        if (player.getNumCoin() < costo)
            throw new NotEnoughMoneyException();
    }

    public void checkFullDining(DiningRoom dining, PawnColor color) throws MaxStudentReachedException {
        if (dining.count(color) == 10)
            throw new MaxStudentReachedException();
    }

    public void checkCorrectTurn(Turn turn, Player player) throws NotYourTurnException {
        if(turn.getCurrentPlayer().equals(player))
            throw new NotYourTurnException();
    }

    public void checkCorrectAction(Player player, PlayerState state) throws WrongPhaseException {
        if (!player.getState().equals(state))
            throw new WrongPhaseException(player.getState());
    }

}
