package hwr.oop
import kotlin.math.abs
class Springer(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "s" else "S"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val startZeile = if (isWhite) 1 else 8

        val deltaX = to.Zeile - from.Zeile
        val deltaY = to.Spalte - from.Spalte

        val destination = board.getFigureAt(to)

        if (((abs(deltaX) == 2 && abs(deltaY) == 1) || (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2))
            && (destination == null)) {
            return true
        }
        return false
    }
}
