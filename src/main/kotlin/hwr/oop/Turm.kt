package hwr.oop

class Turm(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "t" else "T"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val deltaY = to.Zeile - from.Zeile
        val deltaX = to.Spalte - from.Spalte

        // Der Turm kann sich nur horizontal oder vertikal bewegen
        if (deltaX != 0 && deltaY != 0) {
            return false
        }

        // Prüfen, ob der Weg frei ist
        val stepX = if (deltaX == 0) 0 else deltaX / kotlin.math.abs(deltaX)
        val stepY = if (deltaY == 0) 0 else deltaY / kotlin.math.abs(deltaY)

        var current = Position(from.Spalte + stepX, from.Zeile + stepY)
        while (current != to) {
            if (board.getFigureAt(current) != null) {
                return false
            }
            current = Position(current.Spalte + stepX, current.Zeile + stepY)
        }

        // Zielposition prüfen
        val destination = board.getFigureAt(to)
        return destination == null || destination.isWhite != this.isWhite
    }
}