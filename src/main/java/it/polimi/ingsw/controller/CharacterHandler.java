package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.exceptions.GeneralSupplyFinishedException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.VirtualView;

public class CharacterHandler {
    private TurnController turnController;
    private Game game;
    private VirtualView virtualView;
    private Context professorContext;
    private final Context motherNatureContext;
    private Context islandContext;
    private TableHandler tableHandler;
    private BoardHandler boardHandler;
    private IslandController islandController;

    public CharacterHandler(TurnController turnController, Game game, Context professorContext, Context motherNatureContext, Context islandContext, VirtualView virtualView, TableHandler tableHandler, BoardHandler boardHandler, IslandController islandController){
        this.game = game;
        this.virtualView = virtualView;
        this.turnController = turnController;
        this.professorContext = professorContext;
        this.motherNatureContext = motherNatureContext;
        this.islandContext = islandContext;
        this.tableHandler = tableHandler;
        this.boardHandler = boardHandler;
        this.islandController = islandController;
        this.game.setIslandController(islandController);
    }

    public void useCharacter(Player player, int characterPosition){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
        } catch (ClientException e) {
            virtualView.printError(e, player.getNickname());
            return;
        } catch (GeneralSupplyFinishedException ignored){
        }

        if (character.getType().equals(CharacterType.KNIGHT))
            islandController.setPlayer(player);
        character.activateCharacter(professorContext, motherNatureContext, islandContext);

        game.getRound().getTurn().setUsedCharacter(true);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
            character.activateCharacter(game.getTable().getIsland(islandPosition), tableHandler);
        } catch (ClientException e) {
            virtualView.printError(e, player.getNickname());
            return;
        } catch (GeneralSupplyFinishedException ignored){
        }

        game.getRound().getTurn().setUsedCharacter(true);
    }

    public void useCharacter(Player player, int characterPosition, PawnColor color){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
        } catch (ClientException e) {
            virtualView.printError(e, player.getNickname());
            return;
        } catch (GeneralSupplyFinishedException ignored){
        }

        try {
            character.activateCharacter(game, player, color, islandContext, boardHandler);
        } catch (BagIsEmptyException e) {
            game.getRound().setLastRound();
        } finally {
            game.getRound().getTurn().setUsedCharacter(true);
        }
    }

    public void useCharacter(Player player, int characterPosition, int[] colors){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
        } catch (ClientException e) {
            virtualView.printError(e, player.getNickname());
            return;
        } catch (GeneralSupplyFinishedException ignored){
        }

        PawnColor[] color;
        int size = colors.length;
        color = new PawnColor[size];

        for (int i = 0; i < size; i++){
            color[i] = PawnColor.getColor(colors[i]);
        }

        character.activateCharacter(player, color,boardHandler);

        game.getRound().getTurn().setUsedCharacter(true);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition, PawnColor color){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
        } catch (ClientException e) {
            virtualView.printError(e, player.getNickname());
            return;
        } catch (GeneralSupplyFinishedException ignored){
        }

        try {
            character.activateCharacter(game.getTable().getIsland(islandPosition), color);
        } catch (BagIsEmptyException emptyException) {
            game.getRound().setLastRound();
        } finally {
            game.getRound().getTurn().setUsedCharacter(true);
        }
    }
}
