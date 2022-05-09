package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.islandStrategy.IslandContext;
import it.polimi.ingsw.controller.islandStrategy.IslandStrategy;
import it.polimi.ingsw.controller.islandStrategy.IslandStrategyKnight;
import it.polimi.ingsw.controller.islandStrategy.IslandStrategyStandard;
import it.polimi.ingsw.controller.motherNatureStrategy.MotherNatureContext;
import it.polimi.ingsw.controller.motherNatureStrategy.MotherNatureStrategy;
import it.polimi.ingsw.controller.motherNatureStrategy.MotherNatureStrategyStandard;
import it.polimi.ingsw.controller.professorStrategy.ProfessorContext;
import it.polimi.ingsw.controller.professorStrategy.ProfessorStrategy;
import it.polimi.ingsw.controller.professorStrategy.ProfessorStrategyStandard;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.Round;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;
import it.polimi.ingsw.listeners.ModelListener;
import it.polimi.ingsw.server.VirtualView;

import java.util.LinkedList;
import java.util.List;

public class Controller {
    private final TurnController turnController;
    private Context professorContext;
    private final ProfessorStrategy professorStrategyStandard;
    private final Context motherNatureContext;
    private final MotherNatureStrategy motherNatureStrategy;
    private Context islandContext;
    private final IslandStrategy islandStrategy;
    private IslandStrategy islandStrategyMoreInfluence;
    private Game game;
    private BoardHandler boardHandler;
    private TableHandler tableHandler;
    private CharacterHandler characterHandler;
    private VirtualView virtualView;
    private ModelListener modelListener;
    boolean canStart;

    public Controller(){
        canStart = false;
        game = new Game();
        game.setExpertMode(false);
        turnController = new TurnController();

        professorStrategyStandard = new ProfessorStrategyStandard();
        professorContext = new ProfessorContext(professorStrategyStandard);

        motherNatureStrategy = new MotherNatureStrategyStandard();
        motherNatureContext = new MotherNatureContext(motherNatureStrategy);

        islandStrategy = new IslandStrategyStandard();
        islandContext = new IslandContext(islandStrategy);
        islandStrategyMoreInfluence = new IslandStrategyKnight();
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;

        boardHandler = new BoardHandler(game, turnController, professorContext, virtualView);
        tableHandler = new TableHandler(turnController, game, professorContext, motherNatureContext, islandContext, professorStrategyStandard, motherNatureStrategy, islandStrategy, virtualView);
        characterHandler = new CharacterHandler(turnController, game, professorContext, motherNatureContext, islandContext, virtualView, tableHandler, boardHandler, islandStrategyMoreInfluence);
        modelListener=new ModelListener(virtualView);
    }

    public void setNumPlayer(int num) {
        game.setNumPlayer(num);
    }

    public void setExpertMode(boolean bool){
        game.setExpertMode(bool);
    }

    public void addPlayer(Player player) {
        int remaining = game.addPlayer(player);
        if (remaining == 0) {
            canStart = true;
            synchronized (this) {
                this.notify();
            }
        }
    }

    public List<TowerColor> getAvailableColor(){
        List<TowerColor> colors = new LinkedList<>();
        colors.add(TowerColor.WHITE);
        colors.add(TowerColor.BLACK);
        if (game.getNumPlayer() == 3)
            colors.add(TowerColor.GREY);

        for (Player p : game.getPlayers())
            colors.remove(p.getTowerColor());

        return colors;
    }

    public void useAssistant(int position, Player player){
        Round round = game.getRound();
        int weight = player.getDeck().getAssistant(position).getWeight();

        try {
            turnController.checkPermission(round.getTurn(), player, PlayerState.PLANNING);
            sameAssistant(weight, player);
        } catch (ClientException e) {
            virtualView.printError(e, player.getNickname());
            return;
        }

        player.addAssistant(position);

        if (!round.nextPlanningTurn()) {
            if (player.getDeck().isEmpty())
                round.setLastRound();
            round.endPlanningPhase();
        }
    }

    /**
     * Check if the assistant is already used in that turn by other players,
     * if the assistant played was the only option available do nothing
     * if the player has other assistants to play throws an exception
     *
     * @param weight The weight of the assistant card chosen
     * @param player The player that has called the action: useAssistant
     * @throws ClientException When the player try to use the same assistant of others
     */
    private void sameAssistant(int weight, Player player) throws ClientException {
        boolean value = false;
        List<Integer> weights = new LinkedList<>();
        int size = player.getDeck().getSize();

        if (size == 1)
            return;

        for (Player p: game.getPlayers()){
            if (p.getDeck().getSize() == size - 1) {
                weights.add(p.getLastUsed().getWeight());
                if (p.getLastUsed().getWeight() == weight)
                    value = true;
            }
        }

        if (value && size == 2 && game.getRound().getNumTurnDone() == 2) {
            value = false;
            for (Assistant a : player.getDeck().getAssistant())
                if (!weights.contains(a.getWeight())) {
                    value = true;
                    break;
                }
        }

        if (value)
            throw new ClientException(ErrorType.SAME_ASSISTANT);
    }

    public void useStudentDining(Player player, PawnColor color){
        boardHandler.useStudentDining(player, color);
    }

    public void useStudentIsland(Player player, PawnColor color, int position){
        tableHandler.useStudentIsland(player, color, position);
    }

    public void moveMotherNature(Player player, int endPosition) {
        tableHandler.moveMotherNature(player, endPosition);
    }

    public void chooseCloud(Player player, int position){
        tableHandler.chooseCloud(player, position);
    }

    public void useCharacter(Player player, int characterPosition){
        characterHandler.useCharacter(player, characterPosition);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition){
        characterHandler.useCharacter(player, characterPosition, islandPosition);
    }

    public void useCharacter(Player player, int characterPosition, PawnColor color){
        characterHandler.useCharacter(player, characterPosition, color);
    }

    public void useCharacter(Player player, int characterPosition, int[] colors){
        player.detach();
        characterHandler.useCharacter(player, characterPosition, colors);
        player.attach(modelListener);
    }

    public void useCharacter(Player player, int characterPosition, int islandPosition, PawnColor color){
        game.getTable().getIsland(islandPosition).detach();
        characterHandler.useCharacter(player, characterPosition, islandPosition, color);
        game.getTable().getIsland(islandPosition).attach(modelListener);

    }

    public Player getPlayerByNickname(String nickname){
        for (Player p: game.getPlayers()){
            if(p.getNickname().equals(nickname)) return p;
        }
        return null;
    }

    public Game getGame() {
        return game;
    }

    public TableHandler getTableHandler() {
        return tableHandler;
    }

    public void gameStart() throws InterruptedException {
        synchronized (this) {
            while (!readyToStart())
                this.wait();

            game.startGame();
            game.getRound().attach(modelListener);
            for (Island i : game.getTable().getIsland()) {
                i.attach(modelListener);
            }
            for (Player p : game.getPlayers()) {
                p.attach(modelListener);
                p.getBoard().getProfessorTable().attach(modelListener);
            }
            game.getRound().getTurn().attach(modelListener);

        }
    }

    private boolean readyToStart(){
        return canStart;
    }

    public BoardHandler getBoardHandler() {
        return boardHandler;
    }
}
