package de.uni_passau.fim.prog2.reversi;

class IllegalMoveException extends RuntimeException {

    public IllegalMoveException() {
        super();
    }

    public IllegalMoveException(String message) {
        super(message);
    }
}
