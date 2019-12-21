package de.uni_passau.fim.prog2.reversi;

public class Reversi implements Board {

    private Player[][] game;
    public static final int MAX_LEVEL = 8;
    private int level = 3;
    private Player nextPlayer;
    private Player firstPlayer;

    public Reversi() {
        game = new Player[Board.SIZE][Board.SIZE];
        firstPlayer = Player.HUMAN;
        nextPlayer = firstPlayer;
        setInitialPosition();
    }

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

    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public Player next() {
        return nextPlayer;
    }

    @Override
    public Reversi move(int row, int col) {
        if (row > 0 && row <= Board.SIZE && col > 0 && col <= Board.SIZE) {
            if (!gameOver()) {
                if (nextPlayer == Player.HUMAN) {
                    Direction bestDirection = legalMove(row, col);

                    if (bestDirection != null) {
                        return executeMove(row, col, bestDirection);
                    } else {
                        return null;
                    }
                } else {
                    throw new IllegalMoveException("Machine Turn!");
                }
            } else {
                throw new IllegalMoveException("Game already over!");
            }
        } else {
            throw new IllegalArgumentException("Row or col is negative" +
                    " or too big!");
        }
    }

    @Override
    public Reversi machineMove() {
        return null;
    }

    @Override
    public void setLevel(int level) {
        if (level > 0 && level <= MAX_LEVEL) {
            this.level = level;
        } else {
            throw new IllegalArgumentException("Level is negative" +
                " or too big!");
        }
    }

    @Override
    public boolean gameOver() {
        return false;
    }

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

    @Override
    public int getNumberOfHumanTiles() {
        return getNumberOfTiles(Player.HUMAN);
    }

    @Override
    public int getNumberOfMachineTiles() {
        return getNumberOfTiles(Player.MACHINE);
    }

    @Override
    public Player getSlot(int row, int col) {
        if (row > 0 && col > 0 && row <= Board.SIZE && col <= Board.SIZE) {
            return game[row - 1][col - 1];
        } else {
            throw new IllegalArgumentException("Row or col is negative" +
                " or too big!");
        }
    }

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

    private void setInitialPosition() {
        game[4][4] = firstPlayer.inverse();
        game[5][4] = firstPlayer;
        game[4][5] = firstPlayer;
        game[5][5] = firstPlayer.inverse();
    }

    private Direction legalMove(int row, int col) {
        assert row > 0 && row <= Board.SIZE && col > 0 && col <= Board.SIZE;

        Direction bestDirection = null;
        int inversedSlots = 0;

        for (Direction direction: Direction.values()) {
            int newRow = row + direction.getX();
            int newCol = col + direction.getY();
            int counter = 0;
            boolean legal = false;
            boolean endLoop = false;

            while (!endLoop && newRow <= Board.SIZE && newCol <= Board.SIZE) {
                if (getSlot(newRow, newCol) == nextPlayer.inverse()) {
                    counter++;
                } else if (getSlot(newRow, newCol) == nextPlayer) {
                    if (counter > 0) {
                        legal = true;
                    }
                    endLoop = true;
                } else {
                    endLoop = true;
                }

                newRow += direction.getY();
                newCol += direction.getX();
            }

            if (legal && counter > inversedSlots) {
                bestDirection = direction;
                inversedSlots = counter;
            }
        }
        return bestDirection;
    }

    private Reversi executeMove(int row, int col, Direction bestDirection) {
        return null;
    }

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
