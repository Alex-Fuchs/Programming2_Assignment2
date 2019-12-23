package de.uni_passau.fim.prog2.reversi;

/**
 * Reversi oder auch Othello genannt ist ein strategisches Brettspiel. Das
 * Spielbrett besteht aus {@code Board.SIZE} x {@code Board.SIZE} Feldern.
 * Auf diese Felder legen die Spieler ihre Steine. Das Spiel sieht einen
 * menschlichen Spieler vs einen Bot vor. Die Züge des Bots werden aufwändig
 * ermittelt. Das Spiel implementiert das interface {@code Board}, mit dessen
 * Funktionalität ein Spiel gespielt werden kann.
 *
 * @version 21.12.19
 * @author -----
 */
public class Reversi implements Board {

    /**
     * Entspricht dem Spielfeld.
     */
    private Player[][] game;

    /**
     * Entspricht dem maximal zugelassenen Level.
     */
    public static final int MAX_LEVEL = 8;

    /**
     * Entspricht dem momentanen Level.
     */
    private int level = 3;

    /**
     * Entspricht dem Spieler, der das Spiel eröffnet hat.
     */
    private Player firstPlayer;

    /**
     * Entspricht dem Spieler, der nun an der Reihe ist.
     */
    private Player nextPlayer;

    /**
     * Entspricht true, falls sowohl der menschliche Spieler als auch die
     * Maschine nicht mehr ziehen konnten, was äquivalent dazu ist, dass das
     * Spiel vorbei ist.
     */
    private boolean gameOver;

    /**
     * Erzeugt ein neues Spiel mit standard Spieleinstellungen.
     */
    public Reversi() {
        game = new Player[Board.SIZE][Board.SIZE];
        firstPlayer = Player.HUMAN;
        nextPlayer = firstPlayer;
        setInitialPosition();
    }

    /**
     * Erzeugt ein neues Spiel, wobei {@code firstPlayer} und {@code level}
     * individuell gesetzt werden kann.
     *
     * @param firstPlayer                   Entspricht dem Spieler, der das
     *                                      Spiel eröffnet.
     * @param level                         Entspricht dem Level der Maschine.
     * @throws IllegalArgumentException     Wird geworfen, falls das level
     *                                      nicht positiv oder zu groß ist
     *                                      oder {@code firstPlayer} nicht
     *                                      definiert ist.
     */
    public Reversi(Player firstPlayer, int level) {
        if (level > 0 && level <= MAX_LEVEL && (firstPlayer == Player.MACHINE
                || firstPlayer == Player.HUMAN)) {
            game = new Player[Board.SIZE][Board.SIZE];
            this.firstPlayer = firstPlayer;
            this.nextPlayer = this.firstPlayer;
            this.level = level;
            setInitialPosition();
        } else {
            throw new IllegalArgumentException("firstPlayer or level" +
                    " is illegal!");
        }
    }

    /**
     * Gibt den Spieler zurück, der das Spiel eröffnet hat.
     *
     * @return      Entspricht dem Eröffner.
     */
    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Gibt den Spieler zurück, der nun an der Reihe ist.
     *
     * @return      Entspricht dem nun ziehenden Spieler.
     */
    @Override
    public Player next() {
        return nextPlayer;
    }

    /**
     * Führt einen Zug des Menschen aus, falls der Mensch ziehen kann
     * und dieser an der Reihe ist. Der Zug wird auf einem Klon durchgeführt,
     * falls dieser legal ist.
     *
     * @param row                           Entspricht der Zeile auf der der
     *                                      Stein gelegt werden soll.
     * @param col                           Entspricht der Spalte auf der der
     *                                      Stein gelegt werden soll.
     * @return                              Falls der Zug legal ist, wird der
     *                                      Zug auf einem Klon durchgeführt
     *                                      und dieser zurückgegeben,
     *                                      ansonsten wird null zurückgegeben.
     * @throws IllegalMoveException         Wird geworfen, falls das Spiel
     *                                      vorbei ist oder der Mensch nicht
     *                                      an der Reihe ist.
     * @throws IllegalArgumentException     Wird geworfen, falls {@code row}
     *                                      oder {@code col} nicht positiv oder
     *                                      zu groß sind.
     * @see                                 #gameOver()
     * @see                                 #checkLegalityOfMove(int, int,
     *                                      Player)
     * @see                                 #executeMove(int, int, Direction)
     */
    @Override
    public Reversi move(int row, int col) {
        if (row > 0 && row <= Board.SIZE && col > 0 && col <= Board.SIZE) {
            if (gameOver()) {
                if (next() == Player.HUMAN) {
                    Direction bestMoveDirection
                            = checkLegalityOfMove(row, col, nextPlayer);

                    if (bestMoveDirection != null) {
                        return executeMove(row, col, bestMoveDirection);
                    } else {
                        return null;
                    }
                } else {
                    throw new IllegalMoveException("Machine Turn!");
                }
            } else {
                throw new IllegalMoveException("Game is already over!");
            }
        } else {
            throw new IllegalArgumentException("Row or col is negative" +
                    " or too big!");
        }
    }


    @Override
    public Reversi machineMove() {
        if (gameOver()) {
            if (next() == Player.MACHINE) {

            } else {
                throw new IllegalMoveException("Human Turn!");
            }
        } else {
            throw new IllegalMoveException("Game is already over!");
        }
    }

    /**
     * Setzt das Level auf einen neuen Wert, der positiv und kleiner gleich
     * dem maximalen Level {@code MAX_LEVEL} ist.
     *
     * @param level     Entspricht dem neuen Level und muss positiv und
     *                  kleiner gleich {@code MAX_LEVEL} sein
     */
    @Override
    public void setLevel(int level) {
        if (level > 0 && level <= MAX_LEVEL) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level is negative" +
                " or too big!");
        }
    }

    /**
     * Gibt zurück, ob das Spiel bereits vorbei ist.
     *
     * @return      Entspricht true, falls das Spiel vorbei ist, andernfalls
     *              false.
     */
    @Override
    public boolean gameOver() {
        return gameOver;
    }

    /**
     * Gibt zurück, wer der Gewinner des Spiels ist und somit mehr Steine auf
     * dem Spielfeld hat. Dies ist nur möglich, falls das Spiel bereits vorbei
     * ist. Ein Unentschieden ist ebenso möglich.
     *
     * @return                          Entspricht dem Spieler, der gewonnen
     *                                  hat. Falls null zurückgegeben wird,
     *                                  ist das Spiel unentschieden.
     * @throws IllegalStateException    Wird geworfen, falls das Spiel noch
     *                                  nicht vorbei ist.
     * @see                             #gameOver()
     * @see                             #getNumberOfHumanTiles()
     * @see                             #getNumberOfMachineTiles()
     */
    @Override
    public Player getWinner() {
        if (gameOver()) {
            int numberOfMachineTiles = getNumberOfMachineTiles();
            int numberOfHumanTiles = getNumberOfHumanTiles();
            if (numberOfHumanTiles > numberOfMachineTiles) {
                return Player.HUMAN;
            } else if (numberOfHumanTiles < numberOfMachineTiles) {
                return Player.MACHINE;
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException("Game is not over!");
        }
    }

    /**
     * Gibt die Anzahl der Steine des Menschen auf dem Spielfeld zurück.
     *
     * @return      Entspricht der Anzahl der Steine.
     * @see         #getNumberOfTiles(Player)
     */
    @Override
    public int getNumberOfHumanTiles() {
        return getNumberOfTiles(Player.HUMAN);
    }

    /**
     * Gibt die Anzahl der Steine der Maschine auf dem Spielfeld zurück.
     *
     * @return      Entspricht der Anzahl der Steine.
     * @see         #getNumberOfTiles(Player)
     */
    @Override
    public int getNumberOfMachineTiles() {
        return getNumberOfTiles(Player.MACHINE);
    }

    /**
     * Gibt die Anzahl der leeren Felder auf dem Spielfeld zurück.
     *
     * @return      Entspricht der Anzahl der leeren Felder.
     * @see         #getNumberOfTiles(Player)
     */
    public int getNumberOfEmptyTiles() {
        return getNumberOfTiles(null);
    }

    /**
     * Gibt den Inhalt des Felds zurück, wobei null für ein leeres Feld steht.
     *
     * @param row                       Entspricht der Zeile auf der der Stein
     *                                  gelegt werden soll.
     * @param col                       Entspricht der Spalte auf der der
     *                                  Stein gelegt werden soll.
     * @return                          Gibt den Spieler des Felds zurück.
     *                                  Falls null zurückgegeben wird,
     *                                  ist das Feld leer.
     * @throws IllegalArgumentException Wird geworfen, falls {@code row} oder
     *                                  {@code col} nicht positiv oder zu groß
     *                                  sind.
     */
    @Override
    public Player getSlot(int row, int col) {
        if (row > 0 && col > 0 && row <= Board.SIZE && col <= Board.SIZE) {
            return game[row - 1][col - 1];
        } else {
            throw new IllegalArgumentException("Row or col is negative" +
                " or too big!");
        }
    }

    /**
     * Klont das gesamte Spielobjekt tief und gibt den Klon darauf zurück.
     *
     * @return                          Entspricht dem Klon des Objekts.
     * @throws IllegalStateException    Wird geworfen, falls {@code Reversi}
     *                                  unvorhergesehen nicht klonbar ist.
     */
    @Override
    public Reversi clone() {
        Reversi copy;
        try {
            copy = (Reversi) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Reversi has to be cloneable!");
        }
        Player[][] gameCopy = game.clone();
        for (int i = 0; i < gameCopy.length; i++) {
            gameCopy[i] = gameCopy[i].clone();
        }
        return copy;
    }

    /**
     * Gibt die kanonische Darstellung des Spielfelds zurück. Dabei steht "."
     * für ein leeres Feld, "O" für die Maschine, "X" für den Menschen.
     *
     * @return          Entspricht der Darstellung des Spielfelds.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < game.length; i++) {
            for (int u = 0; u < game[i]. length; u++) {
                switch (game[i][u]) {
                case HUMAN:
                    stringBuilder.append('X');
                    break;
                case MACHINE:
                    stringBuilder.append('O');
                    break;
                default:
                    stringBuilder.append('.');
                    break;
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Setzt die Anfangsposition des Spielfelds in Abhängigkeit der Größe des
     * Spielfelds.
     *
     * @throws AssertionError   Falls {@code firstPlayer} undefiniert ist,
     *                          kann keine Anfangsposition gesetzt werden.
     */
    private void setInitialPosition() {
        assert firstPlayer != null;

        int median = Board.SIZE / 2;
        game[median][median] = firstPlayer.inverse();
        game[median + 1][median] = firstPlayer;
        game[median][median + 1] = firstPlayer;
        game[median + 1][median + 1] = firstPlayer.inverse();
    }

    /**
     * Prüft, ob ein Zug legal ist, also mindestens ein gegnerischer Stein
     * umgedreht wird und die gegnerischen Steine zwischen zwei eigenen
     * Steinen liegen. Zudem dürfen keine leeren Felder zwischen den eigenen
     * Steinen liegen. Außerdem wird auch die beste Richtung, falls vorhanden,
     * zurückgegeben, damit der Zug ausgeführt werden kann.
     *
     * @param row               Entspricht der Zeile auf der der Stein gelegt
     *                          werden soll.
     * @param col               Entspricht der Spalte auf der der Stein gelegt
     *                          werden soll.
     * @param player            Entspricht dem Spieler, für den geprüft werden
     *                          soll, ob der Zug legal ist.
     * @return                  Falls der Zug legal ist, wird die für den Zug
     *                          beste Himmelsrichtung zurückgegeben.
     *                          Andernfalls wird null zurückgegeben.
     * @throws AssertionError   Falls {@code row} oder {@code col} nicht
     *                          positiv oder zu groß sind, oder der Spieler
     *                          undefiniert ist, kann der Zug nicht
     *                          geprüft werden.
     */
    private Direction checkLegalityOfMove(int row, int col, Player player) {
        assert row > 0 && row <= Board.SIZE && col > 0 && col <= Board.SIZE;
        assert player != null;

        if (getSlot(row, col) != null) {
            return null;
        } else {
            Direction bestMoveDirection = null;
            int inversedSlots = 0;

            for (Direction direction : Direction.values()) {
                int rowToCheck = row + direction.getY();
                int colToCheck = col + direction.getX();
                int counter = 0;
                boolean isLegalOperation = false;
                boolean endLoop = false;

                while (!endLoop && rowToCheck <= Board.SIZE
                        && colToCheck <= Board.SIZE) {
                    if (getSlot(rowToCheck, colToCheck) == player.inverse()) {
                        counter++;
                    } else if (getSlot(rowToCheck, colToCheck) == player) {
                        if (counter > 0) {
                            isLegalOperation = true;
                        }
                        endLoop = true;
                    } else {
                        endLoop = true;
                    }

                    rowToCheck += direction.getY();
                    colToCheck += direction.getX();
                }

                if (isLegalOperation && counter > inversedSlots) {
                    bestMoveDirection = direction;
                    inversedSlots = counter;
                }
            }
            return bestMoveDirection;
        }
    }

    /**
     * Führt einen bereits vorher auf Legalität geprüften Zug auf einem Klon
     * aus, wobei der Zug für {@code nextPlayer} ausgeführt wird. Außerdem
     * wird der nächste Spieler berechnet.
     *
     * @param row               Entspricht der Zeile auf der der Stein gelegt
     *                          werden soll.
     * @param col               Entspricht der Spalte auf der der Stein gelegt
     *                          werden soll.
     * @param direction         Entspricht der Himmelsrichtung, in der Steine
     *                          umgedreht werden müssen. Außerdem muss dies
     *                          immer die beste Richtung sein.
     * @return                  Gibt einen Klon zurück, auf dem der Zug
     *                          ausgeführt werden soll.
     * @throws AssertionError   Falls {@code row}, {@code col} nicht
     *                          positiv oder zu groß sind, direction
     *                          null ist oder nextPlayer null ist, ist keine
     *                          sinnvolle Ausführung möglich.
     * @see                     #setNextPlayer()
     */
    private Reversi executeMove(int row, int col, Direction direction) {
        assert row > 0 && row <= Board.SIZE && col > 0 && col <= Board.SIZE;
        assert direction != null;
        assert nextPlayer != null;

        boolean endLoop = false;
        Reversi copy = clone();
        copy.setNextPlayer();
        row += direction.getY();
        col += direction.getX();

        while (!endLoop && row <= Board.SIZE && col <= Board.SIZE) {
            if (getSlot(row, col) == nextPlayer.inverse()) {
                copy.game[row - 1][col - 1] = nextPlayer;
            } else {
                copy.game[row - 1][col - 1] = nextPlayer.inverse();
                endLoop = true;
            }

            row += direction.getY();
            col += direction.getX();
        }
        return copy;
    }

    /**
     * Setzt nach einem Zug den nächsten Spieler, wobei beachtet werden muss,
     * dass es ebenfalls vorkommen kann, das ein bzw beide Spieler aussetzen
     * müssen. Falls beide aussetzen müssen, ist das Spiel vorbei.
     *
     * @throws AssertionError       Falls der Spieler, der gerade gezogen hat,
     *                              undefiniert ist, kann der Spieler, der nun
     *                              an der Reihe ist, nicht berechnet werden.
     * @see                         #numberOfLegalMoves(Player)
     */
    private void setNextPlayer() {
        assert nextPlayer != null;

        nextPlayer = nextPlayer.inverse();
        if (numberOfLegalMoves(nextPlayer) == 0) {
            if (numberOfLegalMoves(nextPlayer.inverse()) == 0) {
                gameOver = true;
            } else {
                nextPlayer = nextPlayer.inverse();
            }
        }
    }

    /**
     * Prüft, wie viele legale Züge für einen Spieler möglich sind. Falls
     * kein Zug möglich ist, muss ein Spieler aussetzten.
     *
     * @param player            Entspricht dem Spieler, für den die Anzahl der
     *                          legalen Züge berechnet wird.
     * @return                  Es wird die Anzahl an legalen, möglichen Zügen
     *                          zurückgegeben.
     * @throws AssertionError   Falls der Spieler undefiniert ist, kann nicht
     *                          geprüft werden, ob dieser ziehen kann.
     */
    public int numberOfLegalMoves(Player player) {
        assert player != null;

        int counter = 0;
        for (int i = 1; i <= game.length; i++) {
            for (int u = 1; u <= game[i].length; u++) {
                Direction direction = checkLegalityOfMove(i, u, player);

                if (direction != null) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Gibt die Anzahl der Steine des {@code player} auf dem Spielfeld zurück.
     *
     * @param player        Entspricht einen der Spieler oder kann auch null
     *                      sein, was für keinen Stein steht.
     * @return              Gibt Anzahl der Steine auf dem Spielfeld zurück
     *                      oder falls {@code player} null ist, auf wie vielen
     *                      Feldern kein Stein liegt.
     */
    private int getNumberOfTiles(Player player) {
        int counter = 0;
        for (int i = 0; i < game.length; i++) {
            for (int u = 0; u < game[i]. length; u++) {
                if (game[i][u] == player) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
