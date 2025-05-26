package hwr.oop.figures

import hwr.oop.ChessBoard
import hwr.oop.Figure
import hwr.oop.Position


enum class KingDirection(val dx: Int, val dy: Int) {
    RECHTS(1, 0),
    LINKS(-1, 0),
    OBEN(0, 1),
    UNTEN(0, -1),
    RECHTS_OBEN(1, 1),
    RECHTS_UNTEN(1, -1),
    LINKS_OBEN(-1, 1),
    LINKS_UNTEN(-1, -1)
}
val directionsKing = KingDirection.entries

class King(override val isWhite: Boolean) : Figure {
    var firstMove = true
    override fun symbol() = if (isWhite) "k" else "K"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsKing) {
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

    fun kingCastle(): Boolean{
        if (firstMove) {
            firstMove = false
            return true
        }else{
            return false
        }
    }

}