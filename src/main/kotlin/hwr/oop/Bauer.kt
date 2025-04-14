package hwr.oop


class Bauer(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "b" else "B"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        val direction = if (istweiß) -1 else 1
        val startSpalte = if (istweiß) 2 else 7

        val dx = to.Zeile - from.Zeile
        val dy = to.Spalte - from.Spalte

        val destination = board.getFigureAt(to)
        // Normaler Zug
        if (dx == 0 && dy == direction && destination == null) {
            return true
        }

        // Erster Zug (2 Felder)
        if (dx == 0 && dy == 2*direction && destination == null && from.Zeile == startSpalte) {
            return true
        }

        // Schlagzug
        if (kotlin.math.abs(dx) == 1 && dy == direction && destination != null && destination.istweiß != istweiß) {
            return true
        }

        return false
    }
}