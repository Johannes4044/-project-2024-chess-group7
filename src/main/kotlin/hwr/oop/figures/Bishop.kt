package hwr.oop.figures

import hwr.oop.*

class Bishop(private val bishopColor: Color) : Figure {
    private val directionsBishop = listOf(
        Directions.UP_LEFT,
        Directions.DOWN_RIGHT,
        Directions.DOWN_LEFT,
        Directions.UP_RIGHT
    )

    override fun color() = bishopColor

    override fun symbol() = if (bishopColor == Color.WHITE) "l" else "L"

    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()
        for (direction in directionsBishop) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            var current = Position.from(from.column + deltaX, from.row + deltaY)
            while (current != null && current.column in 'a'..'h' && current.row in 1..8) {
                val figureAtCurrent = board.getFigureAt(current)
                if (figureAtCurrent == null) {
                    moves.add(current)
                } else {
                    if (figureAtCurrent.color() != this.color()) {
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