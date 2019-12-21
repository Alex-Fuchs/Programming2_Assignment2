package de.uni_passau.fim.prog2.reversi;

public enum Player {
    HUMAN {
        @Override
        Player inverse() {
            return MACHINE;
        }
    },
    MACHINE {
        @Override
        Player inverse() {
            return HUMAN;
        }
    };

    abstract Player inverse();
}
