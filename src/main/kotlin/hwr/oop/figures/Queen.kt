package hwr.oop.figures

import hwr.oop.*

class Queen(override val color: Color) : Figure {
    private val directionsQueen = listOf(
        Directions.UP,
        Directions.DOWN,
        Directions.LEFT,
        Directions.RIGHT,
        Directions.UP_LEFT,
        Directions.UP_RIGHT,
        Directions.DOWN_LEFT,
        Directions.DOWN_RIGHT
    )
    override fun symbol() = if (color == Color.WHITE) "d" else "D"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsQueen) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            var newColumnIndex = from.column.ordinal + deltaX
            var newRowIndex = from.row.ordinal + deltaY

            while (newColumnIndex in Column.values().indices && newRowIndex in Row.values().indices) {
                val current = Position(
                    Column.values()[newColumnIndex],
                    Row.values()[newRowIndex]
                )
                val destination = board.getFigureAt(current)
                if (destination == null) {
                    moves.add(current)
                } else {
                    if (destination.color != this.color) {
                        moves.add(current)
                    }
                    break
                }
                newColumnIndex += deltaX
                newRowIndex += deltaY
            }
        }

        return moves
    }
}