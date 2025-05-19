package hwr.oop

class Rook(override val isWhite: Boolean) : Figures {
    override fun symbol() = if (isWhite) "t" else "T"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Prüfen aller möglichen Richtungen (horizontal und vertikal)
        val directions = listOf(
            Pair(1, 0),  // Rechts
            Pair(-1, 0), // Links
            Pair(0, 1),  // Oben
            Pair(0, -1)  // Unten
        )

        for ((dx, dy) in directions) {
            var current = Position(from.column + dx, from.row + dy)
            while (current.column in 'a'..'h' && current.row in 1..8) {
                val figureAtCurrent = board.getFigureAt(current)
                if (figureAtCurrent == null) {
                    moves.add(current)
                } else {
                    if (figureAtCurrent.isWhite != this.isWhite) {
                        moves.add(current)
                    }
                    break // Stoppen, wenn eine Figur im Weg ist
                }
                current = Position(current.column + dx, current.row + dy)
            }
        }

        return moves
    }
}