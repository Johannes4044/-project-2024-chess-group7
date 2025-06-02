package hwr.oop.figures

import hwr.oop.*

class King(private val kingColor: Color) : Figure {
    private var firstMove = true
    private val directionsKing = listOf(
        Directions.UP,
        Directions.DOWN,
        Directions.LEFT,
        Directions.RIGHT,
        Directions.UP_LEFT,
        Directions.UP_RIGHT,
        Directions.DOWN_LEFT,
        Directions.DOWN_RIGHT,
    )

    override fun color() = kingColor

    override fun symbol() = if (kingColor == Color.WHITE) "k" else "K"

    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsKing) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            val target = Position.valueOf((from.column + deltaX).toString() + (from.row + deltaY).toString())
            if (target.column in 'a'..'h' && target.row in 1..8) {
                val destination = board.getFigureAt(target)
                if (destination == null || destination.color() != this.color()) {
                    moves.add(target)
                }
            }
        }

        return moves
    }
}