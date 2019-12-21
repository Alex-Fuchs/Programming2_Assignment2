package de.uni_passau.fim.prog2.reversi;

public enum Direction {

    NORTH {
        @Override
        int getX() {
            return 0;
        }

        @Override
        int getY() {
            return 1;
        }
    },
    NORTH_EAST {
        @Override
        int getX() {
            return 1;
        }

        @Override
        int getY() {
            return 1;
        }
    },
    EAST {
        @Override
        int getX() {
            return 1;
        }

        @Override
        int getY() {
            return 0;
        }
    },
    SOUTH_EAST {
        @Override
        int getX() {
            return 1;
        }

        @Override
        int getY() {
            return -1;
        }
    },
    SOUTH {
        @Override
        int getX() {
            return 0;
        }

        @Override
        int getY() {
            return -1;
        }
    },
    SOUTH_WEST {
        @Override
        int getX() {
            return -1;
        }

        @Override
        int getY() {
            return -1;
        }
    },
    WEST {
        @Override
        int getX() {
            return -1;
        }

        @Override
        int getY() {
            return 0;
        }
    },
    NORTH_WEST {
        @Override
        int getX() {
            return -1;
        }

        @Override
        int getY() {
            return 1;
        }
    };

    abstract int getX();

    abstract int getY();
}
