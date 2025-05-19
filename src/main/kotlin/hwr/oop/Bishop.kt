package hwr.oop

import kotlin.math.abs

class Bishop(override val isWhite: Boolean) : Figures {
    override fun symbol() = if (isWhite) "l" else "L"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val deltaY = to.row - from.row
        val deltaX = to.column - from.column

        val destination = board.getFigureAt(to)

        //Move
        return abs(deltaX) == abs(deltaY) && (destination == null || destination.isWhite != this.isWhite)
    }

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
                if (canMove(from, current, board)) {
                    moves.add(current)
                }
                if (board.getFigureAt(current) != null) {
                    break // Stoppen, wenn eine Figur im Weg ist
                }
                current = Position(current.column + dx, current.row + dy)
            }
        }

        return moves
    }
}

