package de.uni_passau.fim.prog2.io;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * {@code Shell} ist eine Utilityklasse, die den Input in der Shell und
 * somit alle Kommandos des Nutzers verarbeitet und diese darauf an
 * {@code ShellToBoard} weiterleitet. Dort werden auch, falls vorhanden, die
 * Parameter geprüft. Es können beliebig viele Parameter angehängt werden,
 * nur die ersten, notwendigen Parameter werden weitergeleitet. Zudem kann
 * statt den Kommandos der Form "c" auch ein Wort mit den Anfangsbuchstaben c
 * geschrieben werden.
 *
 * @version 14.11.19
 * @author ------
 */
final class Shell {

    private Shell() { }

    /**
     * Startet das Programm.
     *
     * @param   args            Startübergabe des Programms
     * @throws  IOException     Falls I/O Probleme bei dem Benutzer bestehen,
     *                          wird die {@code IOException} zur JVM
     *                          weitergeleitet und das Programm wird beendet.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader stdin
        = new BufferedReader(new InputStreamReader(System.in));
        execute(stdin);
    }

    /**
     * Liest Befehle inkl Parameter des Benutzers ein, reagiert auf Befehle
     * mit einer Fehlerausgabe oder leitet diese ggf mit Parameter weiter.
     * Die Parameter werden erst nach Weiterleitung geprüft. Statt den
     * Kommandos "c" werden auch Wörter mit dem Anfangsbuchstaben c
     * akzeptiert.
     *
     * @param   stdin           {@code BufferedReader} aus {@code main},
     *                          wird zum Lesen der Befehle verwendet.
     * @throws  IOException     Falls I/O Probleme bei dem Benutzer bestehen,
     *                          wird eine {@code IOException} zu
     *                          {@code main} geleitet.
     * @see                     ShellToBoard
     */
    private static void execute(BufferedReader stdin) throws IOException {
        boolean quit = false;
        final String prompt = "reversi> ";
        while (!quit) {
            System.out.print(prompt);
            String input = stdin.readLine();
            if (input == null || input.trim().equals("")) {
                ShellToBoard.printError("Please enter a command!");
                continue;
            }
            String[] tokens = input.trim().split("\\s+");
            tokens[0] = tokens[0].toLowerCase();

            switch (tokens[0].charAt(0)) {
            case 'n':
                ShellToBoard.newBoard();
                break;
            case 'h':
                ShellToBoard.help();
                break;
            case 'q':
                quit = true;
                break;
            case 'm':
                final int parameterNumberMove = 2;
                if (tokens.length > parameterNumberMove) {
                    String[] parametersForMove = {tokens[1], tokens[2]};
                    ShellToBoard.move(parametersForMove);
                } else {
                    ShellToBoard.printError("Not enough parameters!");
                }
                break;
            case 'l':
                final int parameterNumberLevel = 1;
                if (tokens.length > parameterNumberLevel) {
                    ShellToBoard.setLevel(tokens[1]);
                } else {
                    ShellToBoard.printError("No parameter!");
                }
                break;
            case 's':
                ShellToBoard.switchPlayerOrder();
                break;
            case 'p':
                ShellToBoard.print();
                break;
            default:
                ShellToBoard.printError("Type help for overview!");
                break;
            }
        }
    }
}

