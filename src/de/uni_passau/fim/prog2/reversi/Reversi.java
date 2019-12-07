package de.uni_passau.fim.prog2.reversi;

public class Reversi implements Board, Cloneable {

    public Reversi() {

    }

    @Override
    public Player getFirstPlayer() {
        return null;
    }

    @Override
    public Player next() {
        return null;
    }

    @Override
    public Board move(int row, int col) {
        return null;
    }

    @Override
    public Board machineMove() {
        return null;
    }

    @Override
    public void setLevel(int level) {

    }

    @Override
    public boolean gameOver() {
        return false;
    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public int getNumberOfHumanTiles() {
        return 0;
    }

    @Override
    public int getNumberOfMachineTiles() {
        return 0;
    }

    @Override
    public Player getSlot(int row, int col) {
        return null;
    }

    @Override
    public Board clone() {
        return null;
    }
}
