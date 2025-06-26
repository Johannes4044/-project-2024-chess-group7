package hwr.oop.figures


import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Direction
import hwr.oop.Figure
import hwr.oop.Position
import hwr.oop.figures.MoveUtils.createEmptyMoves
import hwr.oop.figures.MoveUtils.tryAddMoveInDirection


/**
 * Represents a Knight chess piece.
 *
 * @property knightColor The color of the knight (white or black).
 */
class Knight(private val knightColor: Color) : Figure {
    override var hasMoved: Boolean = false

    // List of all possible movement directions for a knight
    private val directionsKnight = listOf(
        Direction.KNIGHT_UP_LEFT,
        Direction.KNIGHT_UP_RIGHT,
        Direction.KNIGHT_DOWN_LEFT,
        Direction.KNIGHT_DOWN_RIGHT,
        Direction.KNIGHT_LEFT_UP,
        Direction.KNIGHT_LEFT_DOWN,
        Direction.KNIGHT_RIGHT_UP,
        Direction.KNIGHT_RIGHT_DOWN
    )

    /**
     * Returns the color of the knight.
     *
     * @return The color of the knight.
     */
    override fun color() = knightColor

    /**
     * Returns the symbol representing the knight.
     * "s" for white, "S" for black.
     *
     * @return The symbol of the knight.
     */
    override fun symbol() = if (knightColor == Color.WHITE) "s" else "S"

    /**
     * Calculates all valid target positions for the knight from the given position on the current chessboard.
     *
     * The knight moves in an "L" shape: two squares in one direction and then one square perpendicular.
     * The target square must be within the board and not occupied by a piece of the same color.
     *
     * @param from The starting position of the knight.
     * @param board The current chessboard.
     * @return A list of all valid target positions.
     */
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = createEmptyMoves()


        for (direction in directionsKnight) {
            tryAddMoveInDirection(from, direction, board, this.color())?.let { moves.add(it) }
        }
        return moves
    }
}