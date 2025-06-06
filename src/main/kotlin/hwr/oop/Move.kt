package hwr.oop


data class Move(val from: Position, val to: Position, val board: ChessBoard) {

    /**
     * Creates a move from one position to another on the given chessboard.
     *
     * @param from The starting position of the move.
     * @param to The target position of the move.
     * @param board The chessboard on which the move is being made.
     */
    override fun toString(): String {
        return "Zug von ${from.column}${from.row} nach ${to.column}${to.row}"
    }

    /**
     * Checks if the move is valid according to the rules of chess.
     *
     * @return True if the move is valid, false otherwise.
     */
    fun isValid(): Boolean {
        val figure = board.getFigureAt(from) ?: return false
        return figure.availableTargets(from, board).contains(to)
    }

    /**
     * Checks if the move is a capture.
     *
     * A move is considered a capture if there is a figure at the target position,
     * and it is of a different color than the moving figure.
     *
     * @return True if the move is a capture, false otherwise.
     */
    fun isCapture(): Boolean {
        val targetFigure = board.getFigureAt(to)
        val movingFigure = board.getFigureAt(from)
        return targetFigure != null && movingFigure != null && targetFigure.color() != movingFigure.color()
    }

    /**
     * Executes the move on the chessboard.
     *
     * @return True if the move was executed successfully, false otherwise.
     * @throws IllegalArgumentException if the move is invalid.
     */
    fun execute(): Boolean {
        if (!isValid()) {
            error("Ungültiger Zug von ${from.column}${from.row} nach ${to.column}${to.row}")
        }
        return board.move(from, to)
    }
}

