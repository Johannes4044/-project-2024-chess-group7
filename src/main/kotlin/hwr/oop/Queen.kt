package hwr.oop

class Queen(override val isWhite: Boolean) : Figure {
    override fun symbol() = if (isWhite) "d" else "D"

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
            var current = Position((from.column + dx), from.row + dy)
            while (current.column in 'a'..'h' && current.row in 1..8) {
                val destination = board.getFigureAt(current)
                if (destination == null) {
                    moves.add(current)
                } else {
                    if (destination.isWhite != this.isWhite) {
                        moves.add(current)
                    }
                    break
                }
                current = Position((current.column + dx), current.row + dy)
            }
        }

        return moves
    }
}