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

        //Move
        if (abs(deltaX) == abs(deltaY) && (destination == null || destination.isWhite != this.isWhite)) {
            return true
        }
        return false
    }
    }

