package it.polimi.ingsw.network;

import java.io.Serializable;

public enum MessageType implements Serializable {

    ControllerView,

    ModelView,

    ViewController

}