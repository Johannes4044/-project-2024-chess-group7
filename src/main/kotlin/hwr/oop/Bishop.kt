import hwr.oop.ChessBoard
import hwr.oop.Figures
import hwr.oop.Position
import kotlin.math.abs

class Bishop(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "l" else "L"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val deltaY = to.Row - from.Row
        val deltaX = to.Column - from.Column

        val destination = board.getFigureAt(to)
        if (abs(deltaX) != abs(deltaY)) {
            return false
        }
        val stepX = if (deltaX == 0) 0 else deltaX / abs(deltaX)
        val stepY = if (deltaY == 0) 0 else deltaY / abs(deltaY)

        var current = Position(from.Column + stepX, from.Row + stepY)

        while (current != to) {
            if (board.getFigureAt(current) != null) {
                return false
            }
            current = Position(current.Column + stepX, current.Row + stepY)
        }

        //Move
        if (((destination == null || destination.isWhite != this.isWhite))) {
            return true
        }
        return false
    }
    }

