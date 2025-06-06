package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Direction
import hwr.oop.Figure
import hwr.oop.Position


/**
 * Represents a Queen chess piece.
 *
 * @property queenColor The color of the queen (white or black).
 */
class Queen(private val queenColor: Color) : Figure {
    // List of all possible movement directions for a queen (horizontal, vertical, diagonal)
    private val directionQueens = listOf(
        Direction.UP,
        Direction.DOWN,
        Direction.LEFT,
        Direction.RIGHT,
        Direction.UP_LEFT,
        Direction.UP_RIGHT,
        Direction.DOWN_LEFT,
        Direction.DOWN_RIGHT
    )

    /**
     * Returns the color of the queen.
     *
     * @return The color of the queen.
     */
    override fun color() = queenColor


    /**
     * Returns the symbol representing the queen.
     * "d" for white, "D" for black.
     *
     * @return The symbol of the queen.
     */
    override fun symbol() = if (queenColor == Color.WHITE) "d" else "D"

        for (direction in directionsQueen) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            var newColumnIndex = from.column.ordinal + deltaX
            var newRowIndex = from.row.ordinal + deltaY

            while (newColumnIndex in Column.values().indices && newRowIndex in Row.values().indices) {
                val current = Position(
                    Column.values()[newColumnIndex],
                    Row.values()[newRowIndex]
                )
                val destination = board.getFigureAt(current)
                if (destination == null) {
                    moves.add(current)
                } else {
                    if (destination.color != this.color) {
                        moves.add(current)
                    }
                    break
                }
                newColumnIndex += deltaX
                newRowIndex += deltaY
            }
        }


    /**
     * Calculates all valid target positions for the queen from the given position on the current chessboard.
     *
     * The queen can move any number of squares in any direction (horizontal, vertical, diagonal),
     * as long as the path is not blocked by another piece. If the first piece encountered is an opponent's,
     * the queen can capture it.
     *
     * @param from The starting position of the queen.
     * @param board The current chessboard.
     * @return A list of all valid target positions.
     */
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        return MoveUtils.slidingMoves(from, board, directionQueens, queenColor)
    }
}