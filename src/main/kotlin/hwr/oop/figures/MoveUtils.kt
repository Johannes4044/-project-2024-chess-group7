// In MoveUtils.kt
package hwr.oop.figures

import hwr.oop.*

object MoveUtils {
    /**
     * Berechnet alle möglichen Zielpositionen für eine Figur, die sich beliebig weit in den angegebenen Richtungen bewegen kann.
     *
     * @param from Startposition der Figur.
     * @param board Das aktuelle Schachbrett.
     * @param directions Liste der Bewegungsrichtungen.
     * @param color Farbe der Figur.
     * @return Liste aller gültigen Zielpositionen.
     */
    fun slidingMoves(
        from: Position,
        board: ChessBoard,
        directions: List<Direction>,
        color: Color
    ): MutableList<Position> {
        val moves = mutableListOf<Position>()
        for (direction in directions) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            var current = Position.from(from.column + deltaX, from.row + deltaY)
            while (current != null && current.column in 'a'..'h' && current.row in 1..8) {
                val figureAtCurrent = board.getFigureAt(current)
                if (figureAtCurrent == null) {
                    moves.add(current)
                } else {
                    if (figureAtCurrent.color() != color) {
                        moves.add(current)
                    }
                    break
                }
                current = Position.from(current.column + deltaX, current.row + deltaY)
            }
        }
        return moves
    }
}