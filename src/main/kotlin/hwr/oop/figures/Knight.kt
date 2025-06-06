package hwr.oop.figures


import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Row
import hwr.oop.Column
import hwr.oop.Direction
import hwr.oop.Figure
import hwr.oop.Position


/**
 * Represents a Knight chess piece.
 *
 * @property knightColor The color of the knight (white or black).
 */
class Knight(private val knightColor: Color) : Figure {
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
        val moves = mutableListOf<Position>()


        for (direction in directionsKnight) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            val newColumnIndex = from.column.ordinal + deltaX
            val newRowIndex = from.row.ordinal + deltaY

            if (newColumnIndex in Column.values().indices && newRowIndex in Row.values().indices) {
                val target = Position(
                    Column.values()[newColumnIndex],
                    Row.values()[newRowIndex]
                )
                
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