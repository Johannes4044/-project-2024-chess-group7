package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Directions
import hwr.oop.Figure
import hwr.oop.Position

class Rook(private val rookColor: Color) : Figure {
    private val directionsRook = listOf(
        Directions.UP,
        Directions.DOWN,
        Directions.LEFT,
        Directions.RIGHT
    )
    override fun color() = rookColor
    override fun symbol() = if (rookColor == Color.WHITE) "t" else "T"
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsRook) {
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
                    break // Stoppen, wenn eine Figur im Weg ist
                }
                current = Position.from(current.column + deltaX, current.row + deltaY)
            }
        }
        return moves
    }

}