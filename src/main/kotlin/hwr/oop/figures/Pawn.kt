package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Color
import hwr.oop.Figure
import hwr.oop.Position


class Pawn(private val pawnColor: Color) : Figure {
    override fun color() = pawnColor
    override fun symbol() = if (pawnColor == Color.WHITE) "b" else "B"

    override fun availableTargets(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()
        val direction = if (color() == Color.WHITE) 1 else -1
        val startZeile = if (color() == Color.WHITE) 2 else 7

        // Normaler Zug
        val forwardOne = Position.valueOf("${from.column}${from.row + direction}")
        if (board.getFigureAt(forwardOne) == null && forwardOne.row
            in 1..8
        ) {
            moves.add(forwardOne)
        }

        // Erster Zug (2 Felder)
        val forwardTwo = Position.valueOf("${from.column}${from.row + 2 * direction}")
        if (from.row == startZeile && board.getFigureAt(forwardOne) == null && board.getFigureAt(forwardTwo) == null) {
            moves.add(forwardTwo)
        }

        // Schlagzüge
        val attackLeft = Position.valueOf("${(from.column - 1).toChar()}${from.row + direction}")
        val attackRight = Position.valueOf("${(from.column + 1).toChar()}${from.row + direction}")

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