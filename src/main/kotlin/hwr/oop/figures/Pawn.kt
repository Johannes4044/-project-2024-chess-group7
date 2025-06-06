package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Figure
import hwr.oop.Position

/**
 * Represents a Pawn chess piece.
 *
 * @property pawnColor The color of the pawn (white or black).
 */
class Pawn(private val pawnColor: Color) : Figure {
    /**
     * Returns the color of the pawn.
     *
     * @return The color of the pawn.
     */
    override fun color() = pawnColor

    /**
     * Returns the symbol representing the pawn.
     * "b" for white, "B" for black.
     *
     * @return The symbol of the pawn.
     */
    override fun symbol() = if (pawnColor == Color.WHITE) "b" else "B"

    /**
     * Calculates all valid target positions for the pawn from the given position on the current chessboard.
     *
     * The pawn can move forward one square if the square is empty, or two squares from its starting position.
     * It can also capture diagonally to the left or right if an opponent's piece is present.
     *
     * @param from The starting position of the pawn.
     * @param board The current chessboard.
     * @return A list of all valid target positions.
     */
    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()
        val direction = if (color() == Color.WHITE) 1 else -1
        val startZeile = if (color() == Color.WHITE) 2 else 7

        // Normal move: one square forward
        val forwardOne = Position.valueOf("${from.column}${from.row + direction}")
        if (board.getFigureAt(forwardOne) == null && forwardOne.row
            in 1..8
        ) {
            moves.add(forwardOne)
        }

        // First move: two squares forward from starting position
        val forwardTwo = Position.valueOf("${from.column}${from.row + 2 * direction}")
        if (from.row == startZeile && board.getFigureAt(forwardOne) == null && board.getFigureAt(forwardTwo) == null) {
            moves.add(forwardTwo)
        }

        // Capture moves: diagonally left and right
        val attackLeft = Position.valueOf("${(from.column - 1)}${from.row + direction}")
        val attackRight = Position.valueOf("${(from.column + 1)}${from.row + direction}")

        if (attackLeft.column in 'a'..'h' && attackLeft.row in 1..8) {
            val leftTarget = board.getFigureAt(attackLeft)
            if (leftTarget != null && leftTarget.color() != this.color()) {
                moves.add(attackLeft)
            }
        }

        if (attackRight.column in 'a'..'h' && attackRight.row in 1..8) {
            val rightTarget = board.getFigureAt(attackRight)
            if (rightTarget != null && rightTarget.color() != this.color()) {
                moves.add(attackRight)
            }
        }

        return moves
    }
}