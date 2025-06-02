package hwr.oop
import hwr.oop.figures.King
import hwr.oop.figures.Rook



data class Move(val from: Position, val to: Position, val board: ChessBoard) {

    override fun toString(): String {
        return "Zug von ${from.column}${from.row} nach ${to.column}${to.row}"
    }


    fun isValid(): Boolean {
        val figure = board.getFigureAt(from) ?: return false
        return figure.availableTargets(from, board).contains(to)
    }

    fun isCapture(): Boolean {
        val targetFigure = board.getFigureAt(to)
        val movingFigure = board.getFigureAt(from)
        return targetFigure != null && movingFigure != null && targetFigure.color() != movingFigure.color()
    }

    fun execute(): Boolean {
        if (!isValid()) {
            error("Ungültiger Zug von ${from.column}${from.row} nach ${to.column}${to.row}")
            return false
        }


        return board.move(from, to)
    }
}

