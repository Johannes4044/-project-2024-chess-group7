package hwr.oop.figures

import hwr.oop.*

/**
 * Represents a Bishop chess piece.
 *
 * @property bishopColor The color of the bishop (white or black).
 */
class Bishop(private val bishopColor: Color) : Figure {
    override var hasMoved: Boolean = false

    // Directions in which a bishop can move (diagonally)
    private val directionBishops = listOf(
        Direction.UP_LEFT,
        Direction.DOWN_RIGHT,
        Direction.DOWN_LEFT,
        Direction.UP_RIGHT
    )

    /**
     * Returns the color of the bishop.
     *
     * @return The color of the bishop.
     */
    override fun color() = bishopColor

    /**
     * Returns the symbol representing the bishop.
     * "l" for white, "L" for black.
     *
     * @return The symbol of the bishop.
     */
    override fun symbol() = if (bishopColor == Color.WHITE) "l" else "L"

    /**
     * Calculates all valid target positions for the bishop from the given position on the current chessboard.
     *
     * The bishop can move diagonally in all four directions until it encounters another piece
     * or leaves the board. Own pieces block the path, opponent pieces can be captured.
     *
     * @param from The starting position of the bishop.
     * @param board The current chessboard.
     * @return A list of all valid target positions.
     */
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        return MoveUtils.slidingMoves(from, board, directionBishops, bishopColor)
    }
}