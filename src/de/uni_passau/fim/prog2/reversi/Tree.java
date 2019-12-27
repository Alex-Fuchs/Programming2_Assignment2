package de.uni_passau.fim.prog2.reversi;

class Tree {

    /**
     * Entspricht der Wurzel des Baumes.
     */
    private Reversi root;

    /**
     * Entspricht den Kinderbäumen der Wurzel.
     */
    private Tree[] children;

    /**
     * Entspricht dem Spieler, der durch seinen Zug die Wurzel erschaffen kann.
     * Wird in der Bewertung benötigt, um zu wissen, durch welchen Zug wessen
     * Spieler eine Spielsituation entstehen kann.
     * Da die Ausgangsspielsituation bereits geschehen ist, muss diese nicht
     * mehr bewertet werden und somit bleibt dort dieses Attribut {@code null}.
     */
    private Player lastMovingPlayer;

    /**
     * Kreiert den gesamten Baum des Ausgangsspielfeldes, wobei durch den Baum
     * alle möglichen Züge simuliert werden und so für die Maschine der beste
     * Zug ausgewählt wird. Das Spiel darf somit nicht vorbei sein und die
     * Maschine muss der nächst ziehende Spieler sein.
     *
     * @param root          Entspricht dem Ausgangsspielfeldes, bei dem die
     *                      Maschine nun ziehen muss.
     * @param level         Entspricht der Schwierigkeitsstufe nach der der
     *                      beste Zug der Maschine berechnet wird.
     */
    Tree(Reversi root, int level) {
        assert root != null && level > 0;
        assert !root.gameOver() && root.next() == Player.MACHINE;

        this.root = root;
        buildTree(level);
    }

    /**
     * Kreiert einen Teilbaum, wobei die Spielsituation, der zuletzt gezogene
     * Spieler und nachfolgend möglichen Spielsituationen gespeichert werden.
     *
     * @param root                  Entspricht der Spielsituation, von der
     *                              alle nachfolgenden Züge berechnet werden.
     * @param lastMovingPlayer      Entspricht dem zuletzt gezogenen Spieler,
     *                              der durch seinen Zug {@code root}
     *                              geschaffen hat.
     */
    private Tree(Reversi root, Player lastMovingPlayer) {
        assert root != null && lastMovingPlayer != null;

        this.root = root;
        this.lastMovingPlayer = lastMovingPlayer;
    }

    /**
     * Baut den gesamten Baum der möglichen Züge von der Ausgangsspielsituation
     * auf, wobei von jeder Spielsituation die nächst möglichen berechnet
     * werden. Die max Tiefe des Baumes ist dabei durch das Level gegeben.
     *
     * @param level         Entspricht der Schwierigkeitsstufe und somit
     *                      der max Tiefe des Baumes.
     */
    private void buildTree(int level) {
        if (!root.gameOver() && level > 0) {
            children = new Tree[root.numberOfLegalMoves(root.next())];

            int index = 0;
            for (int i = 1; i <= Board.SIZE; i++) {
                for (int u = 1; u <= Board.SIZE; u++) {
                    Reversi moveOfNextPlayer = root.moveForNextPlayer(i, u);

                    if (moveOfNextPlayer != null) {
                        children[index] = new Tree(moveOfNextPlayer,
                                root.next());
                        children[index].buildTree(level - 1);
                        index++;
                    }
                }
            }
        } else {
            children = new Tree[0];
        }
    }

    /**
     * Berechnet auf Basis der nächst möglichen Züge den besten Zug für die
     * Maschine.
     *
     * @return          Entspricht dem besten Zug für die Maschine.
     */
    Reversi calculateBestMachineMove() {
        assert !root.gameOver() && root.next() == Player.MACHINE
                && children.length != 0;

        Reversi bestMachineMove = children[0].root;
        double score = Double.MIN_VALUE;
        for (Tree t: children) {
            double scoreOfRoot = t.calculateScore();

            if (scoreOfRoot > score) {
                score = scoreOfRoot;
                bestMachineMove = t.root;
            }
        }
        return bestMachineMove;
    }

    /**
     * Berechnet den Score eines Spielfeldes.
     *
     * @return      Entspricht dem Score von {@code root} aus der Sicht von
     *              {@code lastMovingPlayer}.
     */
    private double calculateScore() {
        assert lastMovingPlayer != null;

        Score score = new Score(root, lastMovingPlayer);
        double scoreOfRoot = score.calculateScore();
        if (children.length > 0) {
            scoreOfRoot += calculateScoreOfChildren(root.next());
        }
        return scoreOfRoot;
    }

    /**
     * Berechnet den Score der Kinder als Teil vom Score von {@code root}.
     * Verhält sich, falls {@code next} der eigene Spieler oder Gegner ist,
     * unterschiedlich.
     *
     * @param next      Entspricht dem Spieler, der durch seinen Zug die Kinder
     *                  erschaffen kann.
     * @return          Gibt den Teilscore für {@code root} zurück.
     */
    private double calculateScoreOfChildren(Player next) {
        assert children.length > 0;

        double result = children[0].calculateScore();
        for (int i = 1; i < children.length; i++) {
            double scoreOfChildren = children[i].calculateScore();

            if ((next == Player.HUMAN && result > scoreOfChildren)
                    || (next == Player.MACHINE && result < scoreOfChildren)) {
                result = scoreOfChildren;
            }
        }
        return result;
    }

}
