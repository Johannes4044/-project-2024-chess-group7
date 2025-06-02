package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position

class Knight(private val knightColor: Color) : Figure {
    private val directionsKnight = listOf(
        Directions.KNIGHT_UP_LEFT,
        Directions.KNIGHT_UP_RIGHT,
        Directions.KNIGHT_DOWN_LEFT,
        Directions.KNIGHT_DOWN_RIGHT,
        Directions.KNIGHT_LEFT_UP,
        Directions.KNIGHT_LEFT_DOWN,
        Directions.KNIGHT_RIGHT_UP,
        Directions.KNIGHT_RIGHT_DOWN
    )

    override fun color() = knightColor
    override fun symbol() = if (knightColor == Color.WHITE) "s" else "S"

    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Ein component davon machen
        for (direction in directionsKnight) {
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