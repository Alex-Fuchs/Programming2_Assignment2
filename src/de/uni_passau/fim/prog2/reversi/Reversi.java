package de.uni_passau.fim.prog2.reversi;

public class Reversi implements Board {

    private Player[][] game;
    private static final int MAX_LEVEL = 8;
    private int level = 3;
    private Player nextPlayer;
    private Player firstPlayer;

    public Reversi() {
        game = new Player[Board.SIZE][Board.SIZE];
        firstPlayer =  Player.HUMAN;
        nextPlayer = firstPlayer;
    }

    public Reversi(Player firstPlayer, int level) {
        game = new Player[Board.SIZE][Board.SIZE];
        this.firstPlayer =  firstPlayer;
        this.nextPlayer = this.firstPlayer;
        this.level = level;
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
        return null;
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
            throw new IllegalArgumentException("Level is negative or too big!");
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
            throw new IllegalArgumentException("Game is not over!");
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
            return game[row][col];
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
            throw new IllegalStateException("Reversi has to be cloneable");
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
        }
        return stringBuilder.toString();
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
