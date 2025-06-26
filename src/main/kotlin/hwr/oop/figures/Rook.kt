package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Direction
import hwr.oop.Figure
import hwr.oop.Position


/**
 * Represents a Rook chess piece.
 *
 * @property rookColor The color of the rook (white or black).
 */
class Rook(private val rookColor: Color) : Figure {
    override var hasMoved: Boolean = false

    // List of all possible movement directions for a rook (horizontal and vertical)
    private val directionRooks = listOf(
        Direction.UP,
        Direction.DOWN,
        Direction.LEFT,
        Direction.RIGHT
    )

    /**
     * Returns the color of the rook.
     *
     * @return The color of the rook.
     */
    override fun color() = rookColor

    /**
     * Returns the symbol representing the rook.
     * "t" for white, "T" for black.
     *
     * @return The symbol of the rook.
     */
    override fun symbol() = if (rookColor == Color.WHITE) "t" else "T"

    /**
     * Calculates all valid target positions for the rook from the given position on the current chessboard.
     *
     * The rook can move any number of squares horizontally or vertically,
     * as long as the path is not blocked by another piece. If the first piece encountered is an opponent's,
     * the rook can capture it.
     *
     * @param from The starting position of the rook.
     * @param board The current chessboard.
     * @return A list of all valid target positions.
     */
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        return MoveUtils.slidingMoves(from, board, directionRooks, rookColor)
    }

}