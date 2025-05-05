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
    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Bewegungsrichtungen der Dame (horizontal, vertikal, diagonal)
        val directions = listOf(
            Pair(1, 0), Pair(-1, 0),  // Horizontal
            Pair(0, 1), Pair(0, -1),  // Vertikal
            Pair(1, 1), Pair(1, -1),  // Diagonal
            Pair(-1, 1), Pair(-1, -1) // Diagonal
        )

        for ((dx, dy) in directions) {
            var current = Position((from.Column + dx).toChar(), from.Row + dy)
            while (current.Column in 'a'..'h' && current.Row in 1..8) {
                val destination = board.getFigureAt(current)
                if (destination == null) {
                    moves.add(current)
                } else {
                    if (destination.isWhite != this.isWhite) {
                        moves.add(current)
                    }
                    break
                }
                current = Position((current.Column + dx).toChar(), current.Row + dy)
            }
        }

        return moves
    }
}