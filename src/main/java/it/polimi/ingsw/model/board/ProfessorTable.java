package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;

import java.util.LinkedList;
import java.util.List;

public class ProfessorTable {
    private LinkedList<Professor> professors;

    public  ProfessorTable(){
        professors = new LinkedList<Professor>();
    }

    public LinkedList<Professor> getProfessors() {
        return professors;
    }

    public void removeProfessor(Professor p){
        professors.remove(p);
    }

    public void addProfessor(Professor p){
        professors.addLast(p);
    }

    public Professor find(PawnColor color){
        for(Professor p: professors){
            if(p.getColor() == color){
                return p;
            }
        }
        return null;
    }
}
