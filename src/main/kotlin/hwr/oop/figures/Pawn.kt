package hwr.oop.figures

import hwr.oop.*
import kotlin.text.get

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

        // Ein Feld vorwärts
        val forwardOneRow = from.row.ordinal + direction
        if (forwardOneRow in Row.values().indices) {
            val forwardOne = Position(from.column, Row.values()[forwardOneRow])
            if (board.getFigureAt(forwardOne) == null) {
                moves.add(forwardOne)

                // Zwei Felder vorwärts (nur vom Startfeld)
                val startRow = if (color() == Color.WHITE) Row.TWO else Row.SEVEN
                if (from.row == startRow) {
                    val forwardTwoRow = from.row.ordinal + 2 * direction
                    if (forwardTwoRow in Row.values().indices) {
                        val forwardTwo = Position(from.column, Row.values()[forwardTwoRow])
                        if (board.getFigureAt(forwardTwo) == null) {
                            moves.add(forwardTwo)
                        }
                    }
                }
            }
        }

        // Schlagen nach links
        val leftCol = from.column.ordinal - 1
        if (leftCol in Column.values().indices && forwardOneRow in Row.values().indices) {
            val attackLeft = Position(Column.values()[leftCol], Row.values()[forwardOneRow])
            val target = board.getFigureAt(attackLeft)
            if (target != null && target.color() != this.color()) {
                moves.add(attackLeft)
            }
            // En Passant nach links
            if (board.enPassantTarget == attackLeft) {
                moves.add(attackLeft)
            }
        }

        // Schlagen nach rechts
        val rightCol = from.column.ordinal + 1
        if (rightCol in Column.values().indices && forwardOneRow in Row.values().indices) {
            val attackRight = Position(Column.values()[rightCol], Row.values()[forwardOneRow])
            val target = board.getFigureAt(attackRight)
            if (target != null && target.color() != this.color()) {
                moves.add(attackRight)
            }
            // En Passant nach rechts
            if (board.enPassantTarget == attackRight) {
                moves.add(attackRight)
            }
        }

        return moves
    }
}