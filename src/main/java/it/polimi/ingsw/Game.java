package it.polimi.ingsw;

public class Game {
    private static Game instance = null;
    private int numPlayer;
    private Table table;
    private Round round;
    private List<Player> player;

    private Game(){ //We make the constructor private, so no one else can create new instances...
    }

    public static Game getInstance(){ //...but allow clients to get an instance via a static method
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    public void startGame(){
    }
    public void endGame(){}

    public boolean isNicknameUsed(){
        return 0;
    }


}

