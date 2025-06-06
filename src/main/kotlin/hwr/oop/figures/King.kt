package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Game
import hwr.oop.Position


class King(override val isWhite: Boolean) : Figure {
    var firstMove = true
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
    override fun symbol() = if (isWhite) "k" else "K"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsKing) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            val target = Position(from.column + deltaX, from.row + deltaY)
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