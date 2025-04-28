package hwr.oop

class Rook(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "t" else "T"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val deltaY = to.Row - from.Row
        val deltaX = to.Column - from.Column

        // Der Turm kann sich nur horizontal oder vertikal bewegen
        if (deltaX != 0 && deltaY != 0) {
            return false
        }

        // Prüfen, ob der Weg frei ist
        val stepX = if (deltaX == 0) 0 else deltaX / kotlin.math.abs(deltaX)
        val stepY = if (deltaY == 0) 0 else deltaY / kotlin.math.abs(deltaY)

        var current = Position(from.Column + stepX, from.Row + stepY)
        while (current != to) {
            if (board.getFigureAt(current) != null) {
                return false
            }
            current = Position(current.Column + stepX, current.Row + stepY)
        }

        // Zielposition prüfen
        val destination = board.getFigureAt(to)
        return destination == null || destination.isWhite != this.isWhite
    }
}