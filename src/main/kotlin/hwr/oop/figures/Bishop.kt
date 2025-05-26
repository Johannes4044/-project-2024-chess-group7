package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position


class Bishop(override val isWhite: Boolean) : Figure {
    private val directionsBishop = listOf(
        Directions.UP_LEFT,   // Rechts oben
        Directions.DOWN_RIGHT,  // Rechts unten
        Directions.DOWN_LEFT,  // Links oben
        Directions.UP_RIGHT // Links unten
    )

    override fun symbol() = if (isWhite) "l" else "L"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()
        // Prüfen aller möglichen diagonalen Richtungen

        for (direction in directionsBishop) {
            val dx = direction.dx
            val dy = direction.dy
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