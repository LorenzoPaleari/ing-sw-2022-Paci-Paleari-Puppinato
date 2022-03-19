package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;

import java.util.List;

public class ProfessorTable {
    private List<Professor> professors;

    public void removeProfessor(Professor p){
        professors.remove(p);
    }

    public void addProfessor(Professor p){
        professors.add(p);
    }

    public Professor find(PawnColor color){
        Professor temp = null;
        //professor = professors.stream().filter(PawnColor::color).findAny().orElse(null);
        return temp;
    }
}
