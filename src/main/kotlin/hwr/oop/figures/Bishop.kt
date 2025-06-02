package hwr.oop.figures

import hwr.oop.*

class Bishop(override val color: Color) : Figure {
    private val directionsBishop = listOf(
        Directions.UP_LEFT,
        Directions.DOWN_RIGHT,
        Directions.DOWN_LEFT,
        Directions.UP_RIGHT
    )

    override fun symbol() = if (color == Color.WHITE) "l" else "L"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()
        for (direction in directionsBishop) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            var current = Position(from.column + deltaX, from.row + deltaY)
            while (current.column in 'a'..'h' && current.row in 1..8) {
                val figureAtCurrent = board.getFigureAt(current)
                if (figureAtCurrent == null) {
                    moves.add(current)
                } else {
                    if (figureAtCurrent.color != this.color) {
                        moves.add(current)
                    }
                    break
                }
                current = Position(current.column + deltaX, current.row + deltaY)
            }
        }
        return moves
    }
}