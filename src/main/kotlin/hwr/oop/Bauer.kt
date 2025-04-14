package hwr.oop


class Bauer(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "b" else "B"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        val direction = if (istweiß) 1 else -1
        val startZeile = if (istweiß) 2 else 7

        val dy = to.Zeile - from.Zeile
        val dx = to.Spalte - from.Spalte

        val destination = board.getFigureAt(to)
        // Normaler Zug
        if (dx == 0 && dy == direction && destination == null) {
            return true
        }

        // Erster Zug (2 Felder)
        if (dx == 0 && dy == 2*direction && destination == null && from.Zeile == startZeile) {
            return true
        }

        // Schlagzug
        if (kotlin.math.abs(dx) == 1 && dy == direction && destination != null && destination.istweiß != this.istweiß) {
            return true
        }

        return false
    }
}