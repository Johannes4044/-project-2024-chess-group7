package hwr.oop.figures

import hwr.oop.*

class Rook(override val color: Color) : Figure {
    private val directionsRook = listOf(
        Directions.UP,
        Directions.DOWN,
        Directions.LEFT,
        Directions.RIGHT
    )
    override fun symbol() = if (color == Color.WHITE) "t" else "T"
    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsRook) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            var newColumnIndex = from.column.ordinal + deltaX
            var newRowIndex = from.row.ordinal + deltaY

            while (newColumnIndex in Column.values().indices && newRowIndex in Row.values().indices) {
                val current = Position(
                    Column.values()[newColumnIndex],
                    Row.values()[newRowIndex]
                )
                val figureAtCurrent = board.getFigureAt(current)
                if (figureAtCurrent == null) {
                    moves.add(current)
                } else {
                    if (figureAtCurrent.color != this.color) {
                        moves.add(current)
                    }
                    break // Stoppen, wenn eine Figur im Weg ist
                }
                newColumnIndex += deltaX
                newRowIndex += deltaY
            }
        }
        return moves
    }

}