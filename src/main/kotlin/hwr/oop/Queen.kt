package hwr.oop

class Queen(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "d" else "D"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val dy = to.Row - from.Row
        val dx = to.Column - from.Column

        // Überprüfung, ob die Bewegung horizontal, vertikal oder diagonal ist
        if (dx != 0 && dy != 0 && kotlin.math.abs(dx) != kotlin.math.abs(dy)) {
            return false
        }

        // Überprüfung, ob der Weg frei ist
        val stepX = if (dx == 0) 0 else dx / kotlin.math.abs(dx)
        val stepY = if (dy == 0) 0 else dy / kotlin.math.abs(dy)

        var current = Position((from.Column + stepX).toChar(), from.Row + stepY)
        while (current != to) {
            if (board.getFigureAt(current) != null) {
                return false
            }
            current = Position((current.Column + stepX).toChar(), current.Row + stepY)
        }

        // Überprüfung der Zielposition
        val destination = board.getFigureAt(to)
        return destination == null || destination.isWhite != this.isWhite

      
    }
}