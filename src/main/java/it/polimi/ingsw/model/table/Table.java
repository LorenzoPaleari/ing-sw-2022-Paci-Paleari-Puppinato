package it.polimi.ingsw.model.table;

import it.polimi.ingsw.controller.TableHandler;
import it.polimi.ingsw.controller.islandController.IslandController;
import it.polimi.ingsw.exceptions.GeneralSupplyFinishedException;
import it.polimi.ingsw.model.character.Character;
import it.polimi.ingsw.model.character.Factory;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.model.pawns.Professor;
import it.polimi.ingsw.model.pawns.Student;

import java.lang.reflect.Method;
import java.util.*;

public class Table {
    private int generalSupply;
    private MotherNature motherNature;
    private List<Cloud> cloud;
    private List<Island> island;
    private List<Character> character;
    private Bag bag;
    private List<Professor> professor;
    private IslandController islandController;

    public Table(int numPlayer, boolean expert, Method islandUpdate, Method checkProfessor, IslandController islandController) {
        motherNature = new MotherNature();
        Factory factory = new Factory();
        this.islandController = islandController;

        bag=new Bag();

        cloud=new LinkedList<>();
        for(int i=1;i<=numPlayer;i++)
            cloud.add(new Cloud());

        fillCloud(numPlayer, bag.initialSetup(numPlayer*(numPlayer+1)));

        island=new LinkedList<>();
        for(int i=1;i<=12;i++)
            island.add(new Island());

        island.get(0).setMotherNature(true);

        List<Student> student = new ArrayList<>();
        for(int i=0;i<5;i++)
            for (int j = 0; j < 2; j++)
                student.add(new Student(i));

        Collections.shuffle(student);
        for (int i = 1; i < 6; i++)
            island.get(i).addStudent(student.get(i - 1));
        for (int i = 7; i < 12; i++)
            island.get(i).addStudent(student.get(i - 2));

        student.clear();

        professor= new LinkedList<>();
        for(int i=0; i<5;i++)
            professor.add(new Professor(i));

        if(expert) {
            generalSupply=20-numPlayer;
            character=new LinkedList<>();

            List<CharacterType> type = Arrays.asList(CharacterType.values());
            Collections.shuffle(type);

            for (int i = 0; i < 3; i++){
                character.add(factory.getCharacter(type.get(i), bag, islandUpdate, checkProfessor, islandController));
            }
        }


    }

    public void fillCloud(int numPlayer, List<Student> student){
        int init = 0;
        int end = numPlayer + 1;
        for (Cloud c: cloud) {
            List<Student> sublist = student.subList(init, end);
            c.addStudent(sublist);
            init = end;
            end += numPlayer + 1;
        }

        student.clear();
    }

    public Bag getBag() {
        return bag;
    }

    public Island getIsland(int position)
    {
        return island.get(position);
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }

    public List<Island> getIsland() {
        return island;
    }

    public int getMotherPosition(){
        return motherNature.getPosition();
    }

    public int getNumIsland () {return island.size();}

    public Character getCharacter(int position) {
        return character.get(position);
    }

    public int getGeneralSupply() {
        return generalSupply;
    }

    public Cloud getCloud(int position){
        return cloud.get(position);
    }

    public void addCoin(int numCoin) {generalSupply=generalSupply+numCoin;}

    public void withdrawCoin() throws GeneralSupplyFinishedException {
        if (generalSupply == 0) throw new GeneralSupplyFinishedException();
        generalSupply -= 1;
    }

    public Professor findProfessor(PawnColor color) {
        Professor temp = null;
        for(Professor p: professor)
        {
            if(p.getColor()==color) {
                temp = p;
            }
        }

        return temp;
    }
    public void moveMotherNature(int moves){
        island.get(getMotherPosition()).setMotherNature(false);
        if (moves + getMotherPosition() > getNumIsland() - 1)
            motherNature.setPosition(moves + getMotherPosition() - getNumIsland());
        else
            motherNature.setPosition(moves + getMotherPosition());

        island.get(getMotherPosition()).setMotherNature(true);
    }

    public void mergeIsland (int position) {
        int positionNext;
        int positionPrev;

        if(position==0)
        {
            positionPrev=getNumIsland()-1;
            positionNext=1;
        }
        else if(position==getNumIsland()-1)
        {
            positionPrev=position-1;
            positionNext=0;
        }
        else
        {
            positionPrev=position-1;
            positionNext=position+1;
        }

        Island islandPrev= island.get(positionPrev);
        Island islandCurr= island.get(position);
        Island islandNext= island.get(positionNext);

        updateIsland(islandPrev, islandCurr);
        updateIsland(islandNext, islandCurr);

        motherNature.setPosition(island.indexOf(islandCurr));
    }

    private void updateIsland(Island islandNotCurr, Island islandCurr) {
        if (islandNotCurr.getIslandTower().size() == 0)
            return;

        if(islandNotCurr.getIslandTower().get(0).getColor()==islandCurr.getIslandTower().get(0).getColor())
        {
            islandCurr.setWeight(islandNotCurr.getWeight()+islandCurr.getWeight());
            islandCurr.getIslandStudent().addAll(islandNotCurr.getIslandStudent());
            islandCurr.getIslandTower() .addAll(islandNotCurr.getIslandTower());
            if (islandCurr.isNoEntryTiles() && islandNotCurr.isNoEntryTiles()) {
                islandCurr.setNoEntryTiles(true);
                for (Character c : character){
                    if (c.getType().equals(CharacterType.GRANDMOTHER_HERBS)) {
                        c.returnNoEntryTiles();
                    }
                }
            }
            else if (islandCurr.isNoEntryTiles() || islandNotCurr.isNoEntryTiles())
                islandCurr.setNoEntryTiles(true);

            island.remove(islandNotCurr);
        }
    }

    public void setCharacter(int position, CharacterType type, Method islandUpdate, Method checkProfessor){
        Factory factory = new Factory();
        character.set(position,factory.getCharacter(type, bag, islandUpdate, checkProfessor, islandController));
    }
}


