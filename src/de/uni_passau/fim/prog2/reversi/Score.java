package de.uni_passau.fim.prog2.reversi;

public class Score {

    private Reversi reversi;
    private static final int[][] fieldScores = getFieldScores();

    public Score(Reversi reversi) {
        this.reversi = reversi;
    }

    public double calculateScore(Player player) {
        double score = 0;
        score += calculateFieldScore(player);
        score += calculateMobilityScore(player);


        return score;
    }

    private double calculateFieldScore(Player player) {
        assert Board.SIZE == 8 && player != null;

        double playerScore = 0;
        double enemyScore = 0;
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

    private double calculateMobilityScore(Player player) {
        assert player != null;

        double playerScore = reversi.numberOfLegalMoves(player);
        double enemyScore = reversi.numberOfLegalMoves(player.inverse());
        double numberOfEmptyFields = reversi.getNumberOfEmptyTiles();

        return (64 / (2 * numberOfEmptyFields))
                * (2.5 * playerScore - 3 * enemyScore);

    }

    private double calculatePotencialScore(Player player) {
        assert player != null;


    }

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
