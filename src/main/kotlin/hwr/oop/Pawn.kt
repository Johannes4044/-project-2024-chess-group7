package hwr.oop


class Pawn(override val isWhite: Boolean) : Figures {
    override fun symbol() = if (isWhite) "b" else "B"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val direction = if (isWhite) 1 else -1
        val startZeile = if (isWhite) 2 else 7

        val deltaY = to.row - from.row
        val deltaX = to.column - from.column

        val destination = board.getFigureAt(to)
        // Normaler Zug
        if (deltaX == 0 && deltaY == direction && destination == null) {
            return true
        }

        // Erster Zug (2 Felder)
        if (deltaX == 0 && deltaY == 2 * direction && destination == null && from.row == startZeile) {
            return true
        }

        // Schlagzug
        if (kotlin.math.abs(deltaX) == 1 && deltaY == direction && destination != null && destination.isWhite != this.isWhite) {
            return true
        }

        return false
    }

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()
        val direction = if (isWhite) 1 else -1
        val startZeile = if (isWhite) 2 else 7

        // Normaler Zug
        val forwardOne = Position(from.column, from.row + direction)
        if (board.getFigureAt(forwardOne) == null && forwardOne.row
            in 1..8
        ) {
            moves.add(forwardOne)
        }

        // Erster Zug (2 Felder)
        val forwardTwo = Position(from.column, from.row + 2 * direction)
        if (from.row == startZeile && board.getFigureAt(forwardOne) == null && board.getFigureAt(forwardTwo) == null) {
            moves.add(forwardTwo)
        }

        // Schlagzüge
        val attackLeft = Position(from.column - 1, from.row + direction)
        val attackRight = Position(from.column + 1, from.row + direction)

        if (attackLeft.column
            in 'a'..'h' && attackLeft.row in 1..8
        ) {
            val leftTarget = board.getFigureAt(attackLeft)
            if (leftTarget != null && leftTarget.isWhite != this.isWhite) {
                moves.add(attackLeft)
            }
        }

        if (attackRight.column in 'a'..'h' && attackRight.row in 1..8) {
            val rightTarget = board.getFigureAt(attackRight)
            if (rightTarget != null && rightTarget.isWhite != this.isWhite) {
                moves.add(attackRight)
            }
        }

        return moves
    }
}