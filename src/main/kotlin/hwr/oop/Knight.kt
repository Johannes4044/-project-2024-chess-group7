package hwr.oop
import kotlin.math.abs

class Knight(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "s" else "S"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val startZeile = if (isWhite) 1 else 8

        val deltaX = to.Row - from.Row
        val deltaY = to.Column - from.Column

        val destination = board.getFigureAt(to)

        if (((abs(deltaX) == 2 && abs(deltaY) == 1) || (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2))
            && (destination == null)) {
            return true
        }
        return false
    }
    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Alle möglichen Bewegungen des Springers
        val directions = listOf(
            Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
            Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2)
        )

        for ((dx, dy) in directions) {
            val target = Position(from.Column + dx, from.Row + dy)
            if (target.Column in 'a'..'h' && target.Row in 1..8) {
                val destination = board.getFigureAt(target)
                if (destination == null || destination.isWhite != this.isWhite) {
                    moves.add(target)
                }
            }
        }

        return moves
    }
}