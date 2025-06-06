package hwr.oop.figures

import hwr.oop.*


class King(override val color: Color) : Figure {
    private var firstMove = true
    private val directionsKing = listOf(
        Directions.UP,
        Directions.DOWN,
        Directions.LEFT,
        Directions.RIGHT,
        Directions.UP_LEFT,
        Directions.UP_RIGHT,
        Directions.DOWN_LEFT,
        Directions.DOWN_RIGHT,
    )
    override fun symbol() = if (color == Color.WHITE) "k" else "K"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsKing) {
            val deltaX = direction.deltaX
            val deltaY = direction.deltaY
            val newColumnIndex = from.column.ordinal + deltaX
            val newRowIndex = from.row.ordinal + deltaY

            if (newColumnIndex in Column.values().indices && newRowIndex in Row.values().indices) {
                val target = Position(
                    Column.values()[newColumnIndex],
                    Row.values()[newRowIndex]
                )
                val destination = board.getFigureAt(target)
                if (destination == null || destination.color != this.color) {
                    moves.add(target)
                }
            }
        }

        return moves
    }
}