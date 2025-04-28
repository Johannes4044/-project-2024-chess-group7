package hwr.oop


class Pawn(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "b" else "B"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        val direction = if (isWhite) 1 else -1
        val startZeile = if (isWhite) 2 else 7

        val deltaY = to.Row - from.Row
        val deltaX = to.Column - from.Column

        val destination = board.getFigureAt(to)
        // Normaler Zug
        if (deltaX == 0 && deltaY == direction && destination == null) {
            return true
        }

        // Erster Zug (2 Felder)
        if (deltaX == 0 && deltaY == 2*direction && destination == null && from.Row == startZeile) {
            return true
        }

        // Schlagzug
        if (kotlin.math.abs(deltaX) == 1 && deltaY == direction && destination != null && destination.isWhite != this.isWhite) {
            return true
        }

        return false
    }
}