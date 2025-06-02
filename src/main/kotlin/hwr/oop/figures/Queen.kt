package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position

class Queen(private val queenColor: Color) : Figure {
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
    override fun color() = queenColor
    override fun symbol() = if (queenColor == Color.WHITE) "d" else "D"

    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsQueen) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            var current = Position.from(from.column + deltaX, from.row + deltaY)
            while (current != null && current.column in 'a'..'h' && current.row in 1..8) {
                val destination = board.getFigureAt(current)
                if (destination == null) {
                    moves.add(current)
                } else {
                    if (destination.color() != this.color()) {
                        moves.add(current)
                    }
                    break
                }
                current = Position.from(current.column + deltaX, current.row + deltaY)
            }
        }

        return moves
    }
}