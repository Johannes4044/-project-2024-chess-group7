package hwr.oop
import kotlin.math.abs

class Knight(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "s" else "S"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val startZeile = if (isWhite) 1 else 8

        val deltaX = to.Row - from.Row
        val deltaY = to.Column - from.Column

        val destination = board.getFigureAt(to)

        if (((abs(deltaX) == 2 && abs(deltaY) == 1) || (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2))
            && (destination == null)) {
            return true
        }
        return false
    }
}