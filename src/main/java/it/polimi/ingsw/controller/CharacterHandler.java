package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandController.IslandControllerMoreInfluence;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.player.Player;

public class CharacterHandler {
    private TurnController turnController;
    private static Game game;
    private static Context professorContext;
    private final Context motherNatureContext;
    private static Context islandContext;

    public CharacterHandler(TurnController turnController, Game game, Context professorContext, Context motherNatureContext, Context islandContext){
        this.game = game;
        this.turnController = turnController;
        this.professorContext = professorContext;
        this.motherNatureContext = motherNatureContext;
        this.islandContext = islandContext;
    }

    public void useCharacter(Player player, int characterPosition){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        if (character.getType().equals(CharacterType.MORE_INFLUENCE))
            IslandControllerMoreInfluence.setPlayer(player);
        character.activateCharacter(professorContext, motherNatureContext, islandContext);

        game.getRound().getTurn().setUsedCharacter(true);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
            character.activateCharacter(game.getTable().getIsland(islandPosition));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        game.getRound().getTurn().setUsedCharacter(true);
    }

    public void useCharacter(Player player, int characterPosition, PawnColor color){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            character.activateCharacter(game, player, color, islandContext);
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        PawnColor[] color;
        int size = colors.length;
        color = new PawnColor[size];

        for (int i = 0; i < size; i++){
            color[i] = PawnColor.getColor(colors[i]);
        }

        character.activateCharacter(player, color);

        game.getRound().getTurn().setUsedCharacter(true);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition, PawnColor color){
        Character character = game.getTable().getCharacter(characterPosition);

        try {
            turnController.checkCharacter(game, player, character.getPrice(), character);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
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
