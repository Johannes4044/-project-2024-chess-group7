package hwr.oop

import kotlin.math.abs

class Knight(override val isWhite: Boolean) : Figures {
    override fun symbol() = if (isWhite) "s" else "S"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val startZeile = if (isWhite) 1 else 8

        val deltaX = to.row - from.row
        val deltaY = to.column - from.column

        val destination = board.getFigureAt(to)

        return (((abs(deltaX) == 2 && abs(deltaY) == 1) || (abs(deltaX) == 1 && abs(deltaY) == 2))
                && (destination == null))
    }

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Alle möglichen Bewegungen des Springers
        val directions = listOf(
            Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
            Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2)
        )

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