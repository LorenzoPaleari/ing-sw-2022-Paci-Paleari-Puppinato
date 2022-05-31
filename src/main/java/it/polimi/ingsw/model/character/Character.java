package it.polimi.ingsw.model.character;

import it.polimi.ingsw.controller.BoardHandler;
import it.polimi.ingsw.controller.Context;
import it.polimi.ingsw.controller.TableHandler;
import it.polimi.ingsw.exceptions.BagIsEmptyException;
import it.polimi.ingsw.exceptions.ClientException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Student;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.Island;

import java.lang.reflect.InvocationTargetException;

public abstract class Character {

    private  CharacterType type;
    private int price;
    private boolean used;
    public int count(PawnColor pawnColor){return -1;}
    public int getNumNoEntryTiles(){return -1;}
    public void returnNoEntryTiles(){}
    public void activateCharacter(Context professorContext, Context motherNatureContext, Context islandContext){}
    public void activateCharacter(Game game, Player player, PawnColor color, Context context, BoardHandler boardHandler) throws BagIsEmptyException{}
    public void activateCharacter(Island island, PawnColor color) throws BagIsEmptyException{}
    public void activateCharacter(Island island, TableHandler tableHandler) throws ClientException{}
    public void activateCharacter(Player player, PawnColor[] color, BoardHandler boardHandler) throws InvocationTargetException, IllegalAccessException{}

    public Character (CharacterType type){
        this.type=type;
        price=type.getPrice();
        used=false;
    }

    public void firstUse(){
        price += 1;
        used = true;
    }
    public boolean isUsed() {
        return used;
    }
    public int getPrice() {
        return price;
    }

    public CharacterType getType() {
        return type;
    }

    public void addStudent(Student s){}
}




