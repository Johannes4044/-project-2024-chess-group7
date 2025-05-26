package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position

class Queen(override val isWhite: Boolean) : Figure {
    private val directionsQueen = listOf(
        Directions.UP,
        Directions.DOWN,
        Directions.LEFT,
        Directions.RIGHT,
        Directions.UP_LEFT,
        Directions.UP_RIGHT,
        Directions.DOWN_LEFT,
        Directions.DOWN_RIGHT
    )
    override fun symbol() = if (isWhite) "d" else "D"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsQueen) {
            val dx = direction.dx
            val dy = direction.dy
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