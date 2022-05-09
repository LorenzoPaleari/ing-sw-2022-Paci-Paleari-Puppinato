package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.model.enumerations.CharacterType;
import it.polimi.ingsw.model.enumerations.PawnColor;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.ViewControllerMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.LobbyHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class UseCharacterRequest implements Serializable, ViewControllerMessage {
    private MessageType type;
    int islandPosition=0;
    PawnColor color=null;
    int[] colors;
    int characterPosition;

    public UseCharacterRequest(int characterPosition) {
        type = MessageType.ViewController;
        this.characterPosition=characterPosition;
    }

    public UseCharacterRequest(int IslandPosition , int characterPosition) {
        type = MessageType.ViewController;
        this.islandPosition = IslandPosition;
        this.characterPosition=characterPosition;
    }

    public UseCharacterRequest(int IslandPosition, PawnColor color, int characterPosition) {
        type = MessageType.ViewController;
        this.islandPosition = IslandPosition;
        this.color = color;
        this.characterPosition=characterPosition;
    }

    public UseCharacterRequest(PawnColor color,  int characterPosition) {
        type = MessageType.ViewController;
        this.color = color;
        this.characterPosition=characterPosition;
    }

    public UseCharacterRequest(int[] colors,  int characterPosition) {
        type = MessageType.ViewController;
        this.colors = colors;
        this.characterPosition=characterPosition;
    }

    @Override
    public MessageType getType() {
        return type;
    }

    @Override
    public void action(View view) {

    }

    @Override
    public void action(LobbyHandler lobbyHandler, ClientHandler clientHandler) {

    }

    @Override
    public void action(VirtualView virtualView, String playerNickname) {
        if(colors!=null)
            virtualView.useCharacter(colors, characterPosition, playerNickname);
        else if (color!=null){
            if(islandPosition!=0)
                virtualView.useCharacter(islandPosition, color, characterPosition, playerNickname);
            else
                virtualView.useCharacter(color, characterPosition, playerNickname);
        }
        else if (islandPosition!=0)
            virtualView.useCharacter(islandPosition, characterPosition, playerNickname);
        else
            virtualView.useCharacter(characterPosition, playerNickname);
    }
}