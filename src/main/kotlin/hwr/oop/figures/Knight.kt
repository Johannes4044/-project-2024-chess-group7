package hwr.oop.figures

import hwr.oop.*

class Knight(override val color: Color) : Figure {
    private val directionsKnight = listOf(
        Directions.KNIGHT_UP_LEFT,
        Directions.KNIGHT_UP_RIGHT,
        Directions.KNIGHT_DOWN_LEFT,
        Directions.KNIGHT_DOWN_RIGHT,
        Directions.KNIGHT_LEFT_UP,
        Directions.KNIGHT_LEFT_DOWN,
        Directions.KNIGHT_RIGHT_UP,
        Directions.KNIGHT_RIGHT_DOWN
    )
    override fun symbol() = if (color == Color.WHITE) "s" else "S"

    override fun availableMoves(from: Position, board: ChessBoard): List<Position> {
        val moves = mutableListOf<Position>()

        for (direction in directionsKnight) {
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