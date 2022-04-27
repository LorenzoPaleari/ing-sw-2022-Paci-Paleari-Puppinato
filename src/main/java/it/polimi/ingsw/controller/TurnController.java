package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.board.DiningRoom;
import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.player.Player;

public class TurnController {

    public void checkCharacter(Game game, Player player, int costo, Character character) throws ClientException, GeneralSupplyFinishedException {
        if(game.getRound().getTurn().isUsedCharacter())
            throw new ClientException(ErrorType.ALREADY_USED_CHARACTER);

        checkPermission(game.getRound().getTurn(), player, PlayerState.ACTION);

        enoughMoney(player, costo);

        player.removeCoin(costo);
        game.getTable().addCoin(costo);

        if (!character.isUsed()) {
            game.getTable().withdrawCoin();
            character.firstUse();
        }
    }

    public void checkPermission(Turn turn, Player player, PlayerState state) throws ClientException {
        checkCorrectTurn(turn, player);
        checkCorrectAction(player, state);
    }

    public void canMove(Turn turn) throws ClientException {
        if (turn.getRemainingMovements() == 0)
            throw new ClientException(ErrorType.WRONG_ACTION);

        turn.updateRemainingMovements();
    }

    public void canMoveMother(Turn turn) throws ClientException {
        if (turn.getRemainingMovements() > 0)
            throw new ClientException(ErrorType.WRONG_ACTION2);
    }

    public void enoughMoney(Player player, int costo) throws ClientException {
        if (player.getNumCoin() < costo)
            throw new ClientException(ErrorType.NOT_ENOUGH_MONEY, costo);
    }

    public void checkFullDining(DiningRoom dining, PawnColor color) throws ClientException {
        if (dining.count(color) == 10)
            throw new ClientException(ErrorType.MAX_STUDENT_REACHED, color);
    }

    public void checkCorrectTurn(Turn turn, Player player) throws ClientException {
        if(!turn.getCurrentPlayer().equals(player))
            throw new ClientException(ErrorType.NOT_YOUR_TURN);
    }

    public void checkCorrectAction(Player player, PlayerState state) throws ClientException {
        if (!player.getState().equals(state)) {
            switch (player.getState()){
                case WAIT: throw new ClientException(ErrorType.WRONG_PHASE);
                case ACTION: throw new ClientException(ErrorType.WRONG_PHASE2);
                case ENDTURN: throw new ClientException(ErrorType.WRONG_PHASE3);
            }
        }
    }

    public void checkCloud(Round round, int position) throws ClientException {
        if (round.getCloudChosen().contains(position))
            throw new ClientException(ErrorType.ALREADY_CHOSEN_CLOUD);
    }
}
