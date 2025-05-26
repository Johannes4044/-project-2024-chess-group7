package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Figure
import hwr.oop.Position


enum class QueenDirection(val delta: Pair<Int, Int>) {
    RIGHT(Pair(1, 0)),
    LEFT(Pair(-1, 0)),
    UP(Pair(0, 1)),
    DOWN(Pair(0, -1)),
    UP_RIGHT(Pair(1, 1)),
    DOWN_RIGHT(Pair(1, -1)),
    UP_LEFT(Pair(-1, 1)),
    DOWN_LEFT(Pair(-1, -1))
}
val directionsQueen = QueenDirection.entries

class Queen(override val isWhite: Boolean) : Figure {
    override fun symbol() = if (isWhite) "d" else "D"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsQueen) {
            val (dx, dy) = direction.delta
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