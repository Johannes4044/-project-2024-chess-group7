import hwr.oop.ChessBoard
import hwr.oop.Figuren
import hwr.oop.Position
import kotlin.math.abs

class Laeufer(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "l" else "L"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val deltaY = to.Zeile - from.Zeile
        val deltaX = to.Spalte - from.Spalte

        val destination = board.getFigureAt(to)

        //Move
        if (abs(deltaX) == abs(deltaY) && (destination == null || destination.isWhite != this.isWhite)) {
            return true
        }
        return false
    }
    }

