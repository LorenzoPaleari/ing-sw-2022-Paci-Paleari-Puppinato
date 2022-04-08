package it.polimi.ingsw.controllerTest;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.pawns.Tower;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.MotherNature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterHandlerTest {
    private Controller controller;
    Player player1 = new Player("TEST1", TowerColor.WHITE);
    Player player2= new Player("TEST2", TowerColor.BLACK);
    private Game game;

    @BeforeEach
    void setUp(){
        controller = new Controller();
        controller.setNumPlayer(2);
        controller.setExpertMode(true);

        controller.addPlayer(player1);
        controller.addPlayer(player2);

        MotherNature.getInstance().setPosition(0);

        game = controller.getGame();

        controller.useAssistant(0,player1);
        controller.useAssistant(5, player2);
    }

    @AfterEach
    void tearDown(){
        controller = null;
        game = null;
    }

    @Test
    void characterErrors(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        game.getTable().setCharacter(0, CharacterType.NO_TOWER);
        game.getTable().setCharacter(1, CharacterType.ADD_MOVES);

        controller.useCharacter(player1, 0);
        assertEquals("You don't have enough money to use this character (Cost = 3)\n", outContent.toString());
        outContent.reset();

        controller.useCharacter(player1, 0, PawnColor.BLUE);
        assertEquals("You don't have enough money to use this character (Cost = 3)\n", outContent.toString());
        outContent.reset();

        int colors[] = new int[1];
        colors[0] = 1;
        controller.useCharacter(player1, 0, colors);
        assertEquals("You don't have enough money to use this character (Cost = 3)\n", outContent.toString());
        outContent.reset();

        controller.useCharacter(player1, 0, 6, PawnColor.BLUE);
        assertEquals("You don't have enough money to use this character (Cost = 3)\n", outContent.toString());
        outContent.reset();

        player1.addCoin();
        controller.useCharacter(player1, 1);
        controller.useCharacter(player1, 1);
        assertEquals("E' già stato usato un personaggio in questo turno\n", outContent.toString());
    }

    @Test
    void useNoEntryTilesCharacter(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        game.getTable().setCharacter(0, CharacterType.NO_ENTRY_TILES);

        for (int i = 0; i < 7; i++)
            for (Player p : game.getPlayers())
                p.addCoin();
        //Aggiungo abbastanza monete da poter usare i personaggi quante volte desidero

        controller.useCharacter(player1, 0, 0);
        assertTrue(game.getTable().getIsland(0).isNoEntryTiles());
        assertEquals(3, game.getTable().getCharacter(0).getPrice());

        //Esaurisco le carte NoEntryTiles
        game.getRound().nextActionTurn();
        controller.useCharacter(player2, 0, 3);
        game.getRound().nextActionTurn();
        game.getRound().endRound();
        assertEquals("", outContent.toString());
        controller.useAssistant(4,player1);
        controller.useAssistant(2, player2);
        controller.useCharacter(player2, 0, 1);
        game.getRound().nextActionTurn();
        controller.useCharacter(player1, 0, 2);

        assertEquals("", outContent.toString());
        game.getRound().nextActionTurn();
        game.getRound().endRound();
        controller.useAssistant(6, player2);
        controller.useAssistant(1, player1);

        controller.useCharacter(player1, 0, 5);
        assertEquals("You don't have any other No Entry Tile left or the chosen island has already one No Entry Tile on\n", outContent.toString());
    }

    @Test
    void useAdd_MovesCharacter(){
        game.getTable().setCharacter(0, CharacterType.ADD_MOVES);

        List<Tower> towers = new LinkedList<>();
        towers.add(new Tower(TowerColor.WHITE));
        game.getRound().getTurn().resetRemainingMovements(0); //Forzo il movimento degli studenti
        controller.moveMotherNature(player1, 3);
        assertEquals(0, game.getTable().getMotherPosition()); //Non si è mossa

        controller.useCharacter(player1, 0);  //Testo che io possa muovere madre natura di 3 caselle e non solo di 1
        controller.moveMotherNature(player1, 5);  //Errore
        game.getTable().getIsland(3).addTower(towers);
        controller.moveMotherNature(player1, 3);
        assertEquals(3, game.getTable().getMotherPosition());

        controller.chooseCloud(player1, 0);
        game.getRound().getTurn().resetRemainingMovements(0); //Forzo il movimento degli studenti
        MotherNature.getInstance().setPosition(11);
        game.getTable().getIsland(3).setMotherNature(false);
        game.getTable().getIsland(11).setMotherNature(true);
        player2.addCoin();
        controller.useCharacter(player2, 0);
        controller.moveMotherNature(player2, 1);
        assertEquals(1, game.getTable().getMotherPosition()); //Non si è mossa
    }

    @Test
    void useNo_ColorCharacter(){
        game.getTable().setCharacter(0, CharacterType.NO_COLOR);

        game.getRound().getTurn().resetRemainingMovements(0);
        game.getTable().getIsland(1).getIslandStudent().clear();
        game.getTable().getIsland(1).addStudent(new Student(4)); //Metto studente blu sull'isola
        game.getTable().getIsland(1).addStudent(new Student(4));
        game.getTable().getIsland(1).addStudent(new Student(0));
        game.getTable().getIsland(1).addStudent(new Student(0));
        List<Tower> towers = new LinkedList<>();
        towers.add(new Tower(TowerColor.WHITE));
        game.getTable().getIsland(1).addTower(towers);
        player1.getBoard().getProfessorTable().addProfessor(game.getTable().findProfessor(PawnColor.BLUE)); //Assegno il professore blu
        player2.getBoard().getProfessorTable().addProfessor(game.getTable().findProfessor(PawnColor.GREEN));
        assertEquals(game.getTable().findProfessor(PawnColor.BLUE), player1.getBoard().getProfessorTable().find(PawnColor.BLUE));

        player1.addCoin();
        player1.addCoin();
        controller.useCharacter(player1, 0, PawnColor.BLUE);
        controller.moveMotherNature(player1, 1);
        assertEquals(1, game.getTable().getIsland(1).getIslandTower().size());
        assertEquals(TowerColor.BLACK, game.getTable().getIsland(1).getIslandTower().get(0).getColor());
    }

    @Test
    void useControl_ProfessorCharacter(){
        game.getTable().setCharacter(0, CharacterType.CONTROL_PROFESSOR);

        player1.getBoard().getEntrance().addStudent(new Student(0));
        controller.useStudentDining(player1, PawnColor.GREEN);

        game.getRound().nextActionTurn();
        player2.addCoin();

        player2.getBoard().getEntrance().addStudent(new Student(0));
        controller.useCharacter(player2, 0);
        controller.useStudentDining(player2, PawnColor.GREEN);
        assertEquals(game.getTable().findProfessor(PawnColor.GREEN), player2.getBoard().getProfessorTable().find(PawnColor.GREEN));
        assertEquals(1, game.getTable().findProfessor(PawnColor.GREEN).getNumStudent());
    }

    @Test
    void useFake_Mother_NatureCharacter(){
        game.getTable().setCharacter(0, CharacterType.FAKE_MOTHER_NATURE);

        player1.addCoin();
        player1.addCoin();
        player1.getBoard().getEntrance().addStudent(new Student(1));
        game.getTable().getIsland(10).addStudent(new Student(1));
        controller.useStudentDining(player1, PawnColor.RED);
        controller.useCharacter(player1, 0, 10);
        assertEquals(game.getTable().getIsland(10).getIslandTower().size(), 1);
    }

    @Test
    void useMore_InfluenceCharacter(){
        game.getTable().setCharacter(0, CharacterType.MORE_INFLUENCE);

        player1.getBoard().getEntrance().addStudent(new Student(0));
        player1.getBoard().getEntrance().addStudent(new Student(0));
        player1.getBoard().getEntrance().addStudent(new Student(4));
        List<Tower> towers = new LinkedList<>();
        towers.add(new Tower(TowerColor.WHITE));
        game.getTable().getIsland(6).addTower(towers);
        controller.useStudentIsland(player1, PawnColor.GREEN, 6);
        controller.useStudentIsland(player1, PawnColor.BLUE, 6);
        controller.useStudentDining(player1, PawnColor.GREEN);
        game.getRound().nextActionTurn();

        MotherNature.getInstance().setPosition(4);
        game.getTable().getIsland(0).setMotherNature(false);
        game.getTable().getIsland(4).setMotherNature(true);
        player2.addCoin();

        player2.getBoard().getEntrance().addStudent(new Student(1));
        player2.getBoard().getEntrance().addStudent(new Student(1));
        controller.useStudentDining(player2, PawnColor.RED);
        controller.useStudentIsland(player2, PawnColor.RED, 6);
        game.getRound().getTurn().resetRemainingMovements(0);
        controller.useCharacter(player2, 0);
        controller.moveMotherNature(player2, 6);
        assertEquals(TowerColor.BLACK, game.getTable().getIsland(6).getIslandTower().get(0).getColor());
    }

    @Test
    void useExchangeCharacter(){
        game.getTable().setCharacter(0, CharacterType.STUDENT_EXCHANGE);

        player1.getBoard().getEntrance().addStudent(new Student(0));
        player1.getBoard().getEntrance().addStudent(new Student(1));
        player1.getBoard().getEntrance().addStudent(new Student(2));
        player1.getBoard().getEntrance().addStudent(new Student(2));
        player1.getBoard().getEntrance().addStudent(new Student(3));
        controller.useStudentDining(player1, PawnColor.YELLOW);
        controller.useStudentDining(player1, PawnColor.YELLOW);
        controller.useStudentDining(player1, PawnColor.PINK);

        int colors[] = new int[4];
        colors[0] = 0;  //Entrance -> Dining
        colors[1] = 1;
        colors[2] = 2;  //Dining -> Entrance
        colors[3] = 3;
        controller.useCharacter(player1, 0, colors);
        assertEquals(1, player1.getBoard().getDiningRoom().count(PawnColor.GREEN));
        assertEquals(1, player1.getBoard().getDiningRoom().count(PawnColor.RED));
        assertEquals(1, player1.getBoard().getDiningRoom().count(PawnColor.YELLOW));
        assertEquals(0, player1.getBoard().getDiningRoom().count(PawnColor.PINK));

        assertEquals(1, player1.getBoard().getProfessorTable().find(PawnColor.YELLOW).getNumStudent());
        assertEquals(1, player1.getBoard().getProfessorTable().find(PawnColor.GREEN).getNumStudent());
    }

    @Test
    void useNo_TowerCharacter(){
        game.getTable().setCharacter(0, CharacterType.NO_TOWER);

        List<Tower> towers = new LinkedList<>();
        towers.addAll(player2.getBoard().getTowerCourt().removeTower(2));
        game.getTable().getIsland(1).addTower(towers);
        game.getTable().getIsland(1).getIslandStudent().clear();

        player1.addCoin();
        player1.addCoin();
        player1.getBoard().getEntrance().addStudent(new Student(2));
        player1.getBoard().getEntrance().addStudent(new Student(2));
        controller.useStudentIsland(player1, PawnColor.YELLOW, 1);
        controller.useStudentDining(player1, PawnColor.YELLOW);
        game.getRound().getTurn().resetRemainingMovements(0);

        controller.useCharacter(player1, 0);
        controller.moveMotherNature(player1, 1);
        assertEquals(TowerColor.WHITE, game.getTable().getIsland(1).getIslandTower().get(0).getColor());
        assertEquals(1, game.getTable().getIsland(1).getIslandTower().size());

        assertEquals(8, player2.getBoard().getTowerCourt().getTower().size());
        assertEquals(7, player1.getBoard().getTowerCourt().getTower().size());
    }

    @Test
    void useReturnCharacter(){
        game.getTable().setCharacter(0, CharacterType.RETURN_STUDENT);

        player1.getBoard().getEntrance().addStudent(new Student(4));
        for (int i = 0; i < 3; i++)
            player1.getBoard().getDiningRoom().addStudent(new Student(4));
        player2.getBoard().getDiningRoom().addStudent(new Student(4));
        player2.getBoard().getDiningRoom().addStudent(new Student(4));
        player1.addCoin();
        player1.addCoin();

        controller.useStudentDining(player1, PawnColor.BLUE);
        controller.useCharacter(player1, 0, PawnColor.BLUE);
        assertEquals(1, game.getTable().findProfessor(PawnColor.BLUE).getNumStudent());
        assertEquals(0, player2.getBoard().getDiningRoom().count(PawnColor.BLUE));
        assertEquals(1, player1.getBoard().getDiningRoom().count(PawnColor.BLUE));

        game.getRound().nextActionTurn();
        for (int i = 0; i < 3; i++)
            player2.addCoin();
        controller.useCharacter(player2, 0, PawnColor.BLUE);
        assertEquals(0, player1.getBoard().getDiningRoom().count(PawnColor.BLUE));
        assertEquals(0, player1.getBoard().getProfessorTable().find(PawnColor.BLUE).getNumStudent());
    }

    @Test
    void useAdd_Student_DinningCharacter() throws BagIsEmptyException {
        game.getTable().setCharacter(0, CharacterType.ADD_STUDENT_DINING);

        game.getTable().getCharacter(0).addStudent(new Student(0));
        game.getTable().getBag().withdrawStudent(game.getTable().getBag().getStudent().size());
        player1.addCoin();

        controller.useCharacter(player1, 0, PawnColor.GREEN);
        assertEquals(1, player1.getBoard().getDiningRoom().count(PawnColor.GREEN));
        assertTrue(game.getRound().getLastRound());
    }

    @Test
    void useAdd_Student_IslandCharacter() throws BagIsEmptyException {
        game.getTable().setCharacter(0, CharacterType.ADD_STUDENT_ISLAND);

        game.getTable().getCharacter(0).addStudent(new Student(0));
        game.getTable().getBag().withdrawStudent(game.getTable().getBag().getStudent().size());

        controller.useCharacter(player1, 0, 6, PawnColor.GREEN);
        assertEquals(1, game.getTable().getIsland(6).countStudent(PawnColor.GREEN));
        assertTrue(game.getRound().getLastRound());
    }

    @Test
    void useReplaceCharacter(){
        game.getTable().setCharacter(0, CharacterType.REPLACE_STUDENT);

        List<Student> students = new LinkedList<>();
        students.add(new Student(4));
        students.add(new Student(2));
        game.getTable().getCharacter(0).addStudent(students.get(1));
        game.getTable().getCharacter(0).addStudent(students.get(0));
        player1.getBoard().getEntrance().getStudent().clear();
        player1.getBoard().getEntrance().addStudent(new Student(3));
        player1.getBoard().getEntrance().addStudent(new Student(3));

        int colors[] = new int[6];
        colors[0] = 3;
        colors[1] = 3;
        colors[2] = -1;
        colors[3] = 4;
        colors[4] = 2;
        colors[5] = -1;

        controller.useCharacter(player1, 0, colors);
        assertEquals(students, player1.getBoard().getEntrance().getStudent());
    }
}