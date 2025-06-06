package hwr.oop.figures

import hwr.oop.*

/**
 * Represents a King chess piece.
 *
 * @property kingColor The color of the king (white or black).
 */
class King(private val kingColor: Color) : Figure {

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
        val moves = mutableListOf<Position>()

        for (direction in directionKings) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            val target = Position.valueOf("${(from.column + deltaX)}${(from.row + deltaY)}".uppercase())
            // Checks if the target position is within the valid chessboard boundaries
            if (target.column in 'a'..'h' && target.row in 1..8) {
                val destination = board.getFigureAt(target)
                // Adds the target if it is empty or occupied by an opponent's piece
                if (destination == null || destination.color() != this.color()) {
                    moves.add(target)
                }
            }
        }

        return moves
    }
}