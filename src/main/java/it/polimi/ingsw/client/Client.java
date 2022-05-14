package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLIView;

import it.polimi.ingsw.client.gui.GUIView;
import it.polimi.ingsw.client.viewUtilities.AnsiGraphics;
import javafx.application.Application;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        Scanner scanner = new Scanner(System.in);
        ServerHandler serverHandler = new ServerHandler();
        View view;
        boolean incorrect;
        System.out.print(AnsiGraphics.getTitle()+ "\n");
        System.out.print(AnsiGraphics.createMenu());
        System.out.print(AnsiGraphics.setTitle("GRAPICH INTERFACE SELECTION"));
        System.out.print(AnsiGraphics.putText(" > Choose the interface you want to use [CLI/GUI]", false, true));
        do {
            String selection = scanner.nextLine();
            if ((selection.equalsIgnoreCase("CLI"))) {
                view = new CLIView(serverHandler);
                serverHandler.setView(view);
                view.start();
                incorrect = false;
            } else if ((selection.equalsIgnoreCase("GUI"))) {
                Application.launch(GUIView.class);
                incorrect = false;
            } else {
                System.out.print(AnsiGraphics.putText("  > Invalid choice, try again", true, false));
                incorrect = true;
            }
        }
        while (incorrect);

    }
}
