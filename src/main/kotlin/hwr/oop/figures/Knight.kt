package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Figure
import hwr.oop.Position

class Knight(override val isWhite: Boolean) : Figure {
    override fun symbol() = if (isWhite) "s" else "S"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Alle möglichen Bewegungen des Springers
        val directions = listOf(
            Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
            Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2)
        )
        // Ein component davon machen
        for ((dx, dy) in directions) {
            val target = Position(from.column + dx, from.row + dy)
            if (target.column in 'a'..'h' && target.row in 1..8) {
                val destination = board.getFigureAt(target)
                if (destination == null || destination.isWhite != this.isWhite) {
                    moves.add(target)
                }
            }
        }
        return moves
    }
}