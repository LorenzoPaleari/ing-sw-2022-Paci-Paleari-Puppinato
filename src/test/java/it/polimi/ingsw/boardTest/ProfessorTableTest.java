package it.polimi.ingsw.boardTest;

import it.polimi.ingsw.model.board.ProfessorTable;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfessorTableTest{
    LinkedList<Professor> prof;
    ProfessorTable professorTable;
    @BeforeEach
    public void setUp(){
        prof = new LinkedList<>();
        professorTable = new ProfessorTable();

    }

    @Test
    void find(PawnColor color) {
        setUp();
        Professor prof = new Professor(4);
        assertEquals(prof, professorTable.find(PawnColor.BLUE));
    }


}