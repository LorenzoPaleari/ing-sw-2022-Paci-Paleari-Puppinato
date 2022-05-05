package it.polimi.ingsw.Listener;

import it.polimi.ingsw.server.VirtualView;

public class ModelListener implements Listener {

    private VirtualView virtualView;

    public ModelListener(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    @Override
    public void update() {
        virtualView.printGameBoard(virtualView.getController().getGame());
    }
}
