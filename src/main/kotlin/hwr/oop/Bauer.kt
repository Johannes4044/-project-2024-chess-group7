package hwr.oop


class Bauer(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "b" else "B"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        val direction = if (istweiß) 1 else -1
        val startZeile = if (istweiß) 2 else 7

        val deltaY = to.Zeile - from.Zeile
        val deltaX = to.Spalte - from.Spalte

        val destination = board.getFigureAt(to)
        // Normaler Zug
        if (deltaX == 0 && deltaY == direction && destination == null) {
            return true
        }

        // Erster Zug (2 Felder)
        if (deltaX == 0 && deltaY == 2*direction && destination == null && from.Zeile == startZeile) {
            return true
        }

        // Schlagzug
        if (kotlin.math.abs(deltaX) == 1 && deltaY == direction && destination != null && destination.istweiß != this.istweiß) {
            return true
        }

        return false
    }
}