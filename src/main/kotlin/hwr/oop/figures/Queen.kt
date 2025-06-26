package hwr.oop.figures

import hwr.oop.*

 /**
 * Repräsentiert eine Dame im Schach.
 */
class Queen(private val queenColor: Color) : Figure {
     override var hasMoved: Boolean = false

     // Alle Bewegungsrichtungen der Dame
    private val directionsQueen = listOf(
        Direction.UP,
        Direction.DOWN,
        Direction.LEFT,
        Direction.RIGHT,
        Direction.UP_LEFT,
        Direction.UP_RIGHT,
        Direction.DOWN_LEFT,
        Direction.DOWN_RIGHT
    )

    override fun color() = queenColor

    override fun symbol() = if (queenColor == Color.WHITE) "d" else "D"

    /**
     * Berechnet alle gültigen Zielpositionen für die Dame.
     */
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        return MoveUtils.slidingMoves(from, board, directionsQueen, queenColor)
    }
}