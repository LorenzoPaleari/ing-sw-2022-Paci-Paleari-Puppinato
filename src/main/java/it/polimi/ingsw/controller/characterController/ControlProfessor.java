package it.polimi.ingsw.controller.characterController;


import it.polimi.ingsw.controller.professorController.ProfessorContext;
import it.polimi.ingsw.controller.professorController.ProfessorController;
import it.polimi.ingsw.controller.professorController.ProfessorControllerModified;

//paci
//during this turn, you take control of any number of
//professors even if you have the same number of students
//as the player who currently controls them
public class ControlProfessor extends Decorator{
    private ProfessorController professorController;

    public ControlProfessor(){
        professorController = new ProfessorControllerModified();
    }
    @Override
    public void useCharacter() {
        ProfessorContext.changeProfessorContext(professorController);
    }
}
