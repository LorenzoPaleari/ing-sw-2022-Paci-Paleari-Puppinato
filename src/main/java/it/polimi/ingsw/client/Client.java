package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLIView;

import it.polimi.ingsw.client.gui.JavaFXInit;
import it.polimi.ingsw.client.viewUtilities.AnsiGraphics;
import javafx.application.Application;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

/**
 * Client class
 */
public class Client {
    /**
     * Main of the Client class
     * @param args
     */
    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        Scanner scanner = new Scanner(System.in);
        ServerHandler serverHandler = new ServerHandler();
        View view;
        boolean incorrect;
        System.out.print(AnsiGraphics.getTitle()+ "\n");
        System.out.print(AnsiGraphics.createMenu());
        System.out.print(AnsiGraphics.setTitle("GRAPHIC INTERFACE SELECTION"));
        System.out.print(AnsiGraphics.putText(" > Choose the interface you want to use [CLI/GUI]", false, true));
        do {
            String selection = scanner.nextLine();
            if ((selection.equalsIgnoreCase("CLI"))) {
                view = new CLIView(serverHandler);
                serverHandler.setView(view);
                view.start();
                incorrect = false;
            } else if ((selection.equalsIgnoreCase("GUI"))) {
                Application.launch(JavaFXInit.class);
                incorrect = false;
            } else {
                System.out.print(AnsiGraphics.putText("  > Invalid choice, try again", true, false));
                incorrect = true;
            }

        }
        while (incorrect);

    }
}
/*
    public static void main(String[] args) {
        boolean cliParam = false;
        AnsiConsole.systemInstall();
        Scanner scanner = new Scanner(System.in);
        ServerHandler serverHandler = new ServerHandler();
        View view = null;

        for (String arg : args) {
            if (arg.equals("--cli") || arg.equals("-c")) {
                cliParam = true;
                break;
            }
        }
        if (cliParam) {
            view = new CLIView(serverHandler);
            serverHandler.setView(view);
            view.start();
        } else {
            Application.launch(JavaFXInit.class);
        }
    }
}
*/
