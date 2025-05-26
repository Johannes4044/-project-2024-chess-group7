package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Figure
import hwr.oop.Position

enum class KnightDirection(val dx: Int, val dy: Int) {
    RECHTS_OBEN(2, 1),
    RECHTS_UNTEN(2, -1),
    LINKS_OBEN(-2, 1),
    LINKS_UNTEN(-2, -1),
    OBEN_RECHTS(1, 2),
    OBEN_LINKS(-1, 2),
    UNTEN_RECHTS(1, -2),
    UNTEN_LINKS(-1, -2)
}

val directionsKnight = KnightDirection.entries

class Knight(override val isWhite: Boolean) : Figure {
    override fun symbol() = if (isWhite) "s" else "S"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Ein component davon machen
        for (direction in directionsKnight) {
            val dx = direction.dx
            val dy = direction.dy
            val target = Position(from.column + dx, from.row + dy)
            if (target.column in 'a'..'h' && target.row in 1..8) {
                val destination = board.getFigureAt(target)
                if (destination == null || destination.isWhite != this.isWhite) {
                    moves.add(target)
                }
            }
        }
        return moves
    }
}