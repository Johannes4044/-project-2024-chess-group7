package hwr.oop
import kotlin.math.abs

class König(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "k" else "K"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val direction = 1
        val startZeile = if (istweiß) 1 else 8

        val deltaX = to.Zeile - from.Zeile
        val deltaY = to.Spalte - from.Spalte

        val destination = board.getFigureAt(to)

        if (abs(deltaX) <=1 && abs(deltaY) <= 1 && destination == null) {
            return true
        }
        return false
    }
}