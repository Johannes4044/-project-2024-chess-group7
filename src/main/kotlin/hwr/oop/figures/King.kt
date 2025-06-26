package hwr.oop.figures

import hwr.oop.*
import hwr.oop.figures.MoveUtils.createEmptyMoves
import hwr.oop.figures.MoveUtils.tryAddMoveInDirection

/**
 * Represents a King chess piece.
 *
 * @property kingColor The color of the king (white or black).
 */
class King(private val kingColor: Color) : Figure {
    override var hasMoved: Boolean = false

    // Directions in which a king can move (one square in any direction)
    private val directionKings = listOf(
        Direction.UP,
        Direction.DOWN,
        Direction.LEFT,
        Direction.RIGHT,
        Direction.UP_LEFT,
        Direction.UP_RIGHT,
        Direction.DOWN_LEFT,
        Direction.DOWN_RIGHT,
    )

    /**
     * Returns the color of the king.
     *
     * @return The color of the king.
     */
    override fun color() = kingColor

    /**
     * Returns the symbol representing the king.
     * "k" for white, "K" for black.
     *
     * @return The symbol of the king.
     */
    override fun symbol() = if (kingColor == Color.WHITE) "k" else "K"

    /**
     * Calculates all valid target positions for the king from the given position on the current chessboard.
     *
     * The king can move one square in any direction, as long as the target square is within the board
     * and not occupied by a piece of the same color.
     *
     * @param from The starting position of the king.
     * @param board The current chessboard.
     * @return A list of all valid target positions.
     */
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = createEmptyMoves()

        for (direction in directionKings) {
            tryAddMoveInDirection(from, direction, board, this.color())?.let { moves.add(it) }
        }

        return moves
    }
}