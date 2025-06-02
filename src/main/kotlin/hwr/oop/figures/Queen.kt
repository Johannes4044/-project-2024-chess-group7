package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position

class Queen(override val color: Color) : Figure {
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
    override fun symbol() = if (color == Color.WHITE) "d" else "D"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsQueen) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            var current = Position((from.column + deltaX), from.row + deltaY)
            while (current.column in 'a'..'h' && current.row in 1..8) {
                val destination = board.getFigureAt(current)
                if (destination == null) {
                    moves.add(current)
                } else {
                    if (destination.color != this.color) {
                        moves.add(current)
                    }
                    break
                }
                current = Position((current.column + deltaX), current.row + deltaY)
            }
        }

        return moves
    }
}