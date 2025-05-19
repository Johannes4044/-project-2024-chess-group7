package hwr.oop

data class Move(val from: Position, val to: Position, val board: ChessBoard) {
    override fun toString(): String {
        return "Zug von ${from.column}${from.row} nach ${to.column}${to.row}"
    }

    fun isValid(): Boolean {
        val figure = board.getFigureAt(from) ?: return false
        return figure.availableMoves(from, board).contains(to)
    }

    fun isCapture(): Boolean {
        val targetFigure = board.getFigureAt(to)
        val movingFigure = board.getFigureAt(from)
        return targetFigure != null && movingFigure != null && targetFigure.isWhite != movingFigure.isWhite
    }

    fun execute(): Boolean {
        if (!isValid()) {
            println("Ungültiger Zug!")
            return false
        }

        if (isCapture()) {
            println("Figur wurde geschlagen!")
        }

        return board.move(from, to)
    }
}
