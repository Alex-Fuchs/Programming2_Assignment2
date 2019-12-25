package de.uni_passau.fim.prog2.reversi;

/**
 * {@code Score} berechnet für eine Instanz der Klasse {@code Reversi} den
 * Score eines {@code Player}. Diese Bewertung dient als Entscheidungsgrundlage
 * für die Maschine. Die Klasse {@code Score} muss abgeändert werden, falls
 * die Größe des Spielbretts nicht 8 x 8 beträgt, da diese in der Klasse
 * hartkodiert wurde.
 *
 * @version 21.12.19
 * @author -----
 */
public class Score {

    /**
     * Entspricht das zu bewertende Spielobjekt.
     */
    private Reversi reversi;

    /**
     * Entspricht der Bewertung der einzelnen Felder des Spielbretts.
     */
    private static final int[][] fieldScores = getFieldScores();

    /**
     * Kreiert ein Bewertungsobjekt für ein {@code Reversi} Objekt, wobei
     * das Bewertungsobjekt keinen {@code Player} speichert, sondern aus der
     * Sicht beider das Spielbrett bewerten kann.
     *
     * @param reversi   Entspricht dem zu bewertenden Spielbrett.
     */
    Score(Reversi reversi) {
        assert reversi != null;

        this.reversi = reversi;
    }

    /**
     * Berechnet den Score aus der Sicht des {@code player}.
     *
     * @param player    Entspricht dem Spieler, für den bewertet wird.
     * @return          Gibt den Score des Spielbretts zurück.
     * @see             #calculateFieldScore(Player)
     * @see             #calculateMobilityScore(Player, int)
     * @see             #calculatePotencialScore(Player, int)
     */
    double calculateScore(Player player) {
        assert player != null;

        double score = 0;
        int numberOfTakenFields = reversi.getNumberOfHumanTiles()
                                + reversi.getNumberOfMachineTiles();

        score += calculateFieldScore(player);
        score += calculateMobilityScore(player, numberOfTakenFields);
        score += calculatePotencialScore(player, numberOfTakenFields);
        return score;
    }

    /**
     * Berechnet den Score der Felder von {@code player}, wobei auch die Felder
     * des Gegners mit einbezogen werden. Jedes Feld wird anders bewertet.
     * Diese Bewertungfunktion hat eine Größe von 8 x 8 hartkodiert und kann
     * somit andere Spielbrette nicht bewerten. Der Score wird mit einer
     * bestimmten Formel berechnet.
     *
     * @param player        Entspricht dem Spieler, für den bewertet wird.
     * @return              Gibt den Score der Felder zurück.
     */
    private double calculateFieldScore(Player player) {
        assert Board.SIZE == 8 && player != null;

        int playerScore = 0;
        int enemyScore = 0;
        for (int i = 1; i <= Board.SIZE; i++) {
            for (int u = 1; u <= Board.SIZE; u++) {
                if (reversi.getSlot(i, u) == player) {
                    playerScore += fieldScores[i - 1][u - 1];
                } else if (reversi.getSlot(i, u) == player.inverse()) {
                    enemyScore += fieldScores[i - 1][u - 1];
                }
            }
        }
        return playerScore - 1.5 * enemyScore;
    }

    /**
     * Berechnet den Score der Mobilität, der momentan möglichen Züge von
     * {@code player}, wobei auch die möglichen Züge des Gegners mit einbezogen
     * werden. Der Score wird mit einer bestimmten Formel berechnet und wird
     * im Laufe des Spiels immer unwichtiger.
     *
     * @param player                Entspricht dem Spieler, für den bewertet
     *                              wird.
     * @param numberOfTakenFields   Entspricht der Anzahl an besetzten Feldern,
     *                              die aufgrund Laufzeit ausgelagert wurde.
     * @return                      Gibt den Score der möglichen Züge zurück.
     */
    private double calculateMobilityScore(Player player,
                                          int numberOfTakenFields) {
        assert player != null;

        int numberOfFields = Board.SIZE * Board.SIZE;
        int playerScore = reversi.numberOfLegalMoves(player);
        int enemyScore = reversi.numberOfLegalMoves(player.inverse());
        return (numberOfFields / (double) numberOfTakenFields)
                * (3.0 * playerScore - 4.0 * enemyScore);

    }

    /**
     * Berechnet den Score des Potenzials, der in Zukunft möglichen Züge von
     * {@code player}, wobei auch die zukünftigen, möglichen Züge des Gegners
     * mit einbezogen werden. Der Score wird mit einer bestimmten Formel
     * berechnet und wird im Laufe des Spiels immer unwichtiger.
     *
     * @param player                Entspricht dem Spieler, für den bewertet
     *                              wird.
     * @param numberOfTakenFields   Entspricht der Anzahl an besetzten Feldern,
     *                              die aufgrund Laufzeit ausgelagert wurde.
     * @return                      Gibt den Score der zukünftig möglichen
     *                              Züge zurück.
     * @see                         #countWrappingFields(int, int)
     */
    private double calculatePotencialScore(Player player,
                                           int numberOfTakenFields) {
        assert player != null;

        int numberOfFields = Board.SIZE * Board.SIZE;
        int playerScore = 0;
        int enemyScore = 0;
        for (int i = 1; i <= Board.SIZE; i++) {
            for (int u = 1; u <= Board.SIZE; u++) {
                if (reversi.getSlot(i, u) == player) {
                    enemyScore += countWrappingFields(i, u);
                } else if (reversi.getSlot(i, u) == player.inverse()) {
                    playerScore += countWrappingFields(i, u);
                }
            }
        }
        return (numberOfFields / (2.0 * numberOfTakenFields))
                * (2.5 * playerScore - 3.0 * enemyScore);
    }

    /**
     * Zählt die Anzahl der leeren Felder, die an das Feld in der Zeile
     * {@code row} und in der Spalte {@code col} anliegen.
     *
     * @param row       Entspricht der Zeile des Feldes.
     * @param col       Entspricht der Spalte des Feldes.
     * @return          Gibt die Anzahl der anliegenden Felder zurück.
     */
    private int countWrappingFields(int row, int col) {
        assert row > 0 && row <= Board.SIZE && col > 0 && col <= Board.SIZE;

        int counter = 0;
        for (Direction direction: Direction.values()) {
            int rowToCount = row + direction.getY();
            int colToCount = col + direction.getX();
            if (reversi.getSlot(rowToCount, colToCount) == null) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Gibt eine 8 x 8 Matrix zurück, wobei in jedem Feld der Score des
     * Spielbrettfeldes gespeichert wird. Falls das Spielfeld nicht 8 x 8
     * groß ist, muss eine neue Bewertungsmatrix gebildet werden.
     *
     * @return      Entspricht der Bewertungsmatrix, die für die
     *              Felderbewertung benötigt wird.
     */
    private static int[][] getFieldScores() {
        int[][] result = new int[8][8];

        result[0][0] = 9999;
        result[0][1] = 5;
        result[0][2] = 500;
        result[0][3] = 200;
        result[0][4] = 200;
        result[0][5] = 500;
        result[0][6] = 5;
        result[0][7] = 9999;

        result[1][0] = 5;
        result[1][1] = 1;
        result[1][2] = 50;
        result[1][3] = 150;
        result[1][4] = 150;
        result[1][5] = 50;
        result[1][6] = 1;
        result[1][7] = 5;

        result[2][0] = 500;
        result[2][1] = 50;
        result[2][2] = 250;
        result[2][3] = 100;
        result[2][4] = 100;
        result[2][5] = 250;
        result[2][6] = 50;
        result[2][7] = 500;

        result[3][0] = 200;
        result[3][1] = 150;
        result[3][2] = 100;
        result[3][3] = 50;
        result[3][4] = 50;
        result[3][5] = 100;
        result[3][6] = 150;
        result[3][7] = 200;

        result[4][0] = 200;
        result[4][1] = 150;
        result[4][2] = 100;
        result[4][3] = 50;
        result[4][4] = 50;
        result[4][5] = 100;
        result[4][6] = 150;
        result[4][7] = 200;

        result[5][0] = 500;
        result[5][1] = 50;
        result[5][2] = 250;
        result[5][3] = 100;
        result[5][4] = 100;
        result[5][5] = 250;
        result[5][6] = 50;
        result[5][7] = 500;

        result[6][0] = 5;
        result[6][1] = 1;
        result[6][2] = 50;
        result[6][3] = 150;
        result[6][4] = 150;
        result[6][5] = 50;
        result[6][6] = 1;
        result[6][7] = 5;

        result[7][0] = 9999;
        result[7][1] = 5;
        result[7][2] = 500;
        result[7][3] = 200;
        result[7][4] = 200;
        result[7][5] = 500;
        result[7][6] = 5;
        result[7][7] = 9999;

        return result;
    }
}
