package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLIView;
import it.polimi.ingsw.client.gui.GUIView;
import javafx.application.Application;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServerHandler serverHandler = new ServerHandler();
        View view;
        boolean incorrect;
        do {
            System.out.print("Choose the interface you want to use [CLI/GUI]: ");
            String selection = scanner.nextLine();
            if ((selection.equalsIgnoreCase("CLI"))) {
                view = new CLIView(serverHandler);
                serverHandler.setView(view);
                view.start();
                incorrect = false;
            } else if ((selection.equalsIgnoreCase("GUI"))) {
                view = new GUIView();
                Application.launch(GUIView.class);
                incorrect = false;
            } else {
                System.out.println("Invalid choice. Try again.");
                incorrect = true;
            }
        }
        while (incorrect);

    }
}
