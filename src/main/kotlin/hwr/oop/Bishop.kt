package hwr.oop

import kotlin.math.abs

class Bishop(override val isWhite: Boolean) : Figure {
    override fun symbol() = if (isWhite) "l" else "L"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Prüfen aller möglichen diagonalen Richtungen
        val directions = listOf(
            Pair(1, 1),   // Rechts oben
            Pair(1, -1),  // Rechts unten
            Pair(-1, 1),  // Links oben
            Pair(-1, -1)  // Links unten
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

