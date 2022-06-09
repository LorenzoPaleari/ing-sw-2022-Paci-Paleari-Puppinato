package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.listeners.ModelListener;

import java.util.LinkedList;

public class ProfessorTable {
    private LinkedList<Professor> professors;

    private ModelListener modelListener=null;

    /**
     * Constructor
     * Initialize the professors list
     */
    public  ProfessorTable(){
        professors = new LinkedList<>();
    }

    /**
     * Returns the professors list
     * @return
     */
    public LinkedList<Professor> getProfessors() {
        return professors;
    }

    /**
     * removes a professor from the list
     * @param p
     */
    public void removeProfessor(Professor p){
        professors.remove(p);
    }

    /**
     * adds a professor to the list
     * @param p
     */
    public void addProfessor(Professor p){
        professors.addLast(p);
    }

    /**
     * find a professor of the indicated color
     * @param color
     * @return
     */
    public Professor find(PawnColor color){
        for(Professor p: professors)
            if(p.getColor() == color){
                return p;
            }
        return null;
    }
}
