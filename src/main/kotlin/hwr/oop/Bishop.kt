import hwr.oop.ChessBoard
import hwr.oop.Figures
import hwr.oop.Position
import kotlin.math.abs

class Bishop(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "l" else "L"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val deltaY = to.Row - from.Row
        val deltaX = to.Column - from.Column

        val destination = board.getFigureAt(to)

        //Move
        if (abs(deltaX) == abs(deltaY) && (destination == null || destination.isWhite != this.isWhite)) {
            return true
        }
        return false
    }
    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        // Prüfen aller möglichen diagonalen Richtungen
        val directions = listOf(
            Pair(1, 1),   // Rechts oben
            Pair(1, -1),  // Rechts unten
            Pair(-1, 1),  // Links oben
            Pair(-1, -1)  // Links unten
        )

        for ((dx, dy) in directions) {
            var current = Position(from.Column + dx, from.Row + dy)
            while (current.Column in 'a'..'h' && current.Row in 1..8) {
                if (canMove(from, current, board)) {
                    moves.add(current)
                }
                if (board.getFigureAt(current) != null) {
                    break // Stoppen, wenn eine Figur im Weg ist
                }
                current = Position(current.Column + dx, current.Row + dy)
            }
        }

        return moves
    }
    }

