package hwr.oop.figures

import hwr.oop.*

object MoveUtils {

    fun createEmptyMoves(): MutableList<Position> = mutableListOf()

    /**
    * Calculates all possible target positions for a piece that can move any distance in the given directions.
     */
    fun slidingMoves(
        from: Position,
        board: ChessBoard,
        directions: List<Direction>,
        color: Color
    ): MutableList<Position> {
        val moves = mutableListOf<Position>()
        for (direction in directions) {
            var colIndex = from.column.ordinal + direction.deltaX
            var rowIndex = from.row.ordinal + direction.deltaY
            while (colIndex in Column.values().indices && rowIndex in Row.values().indices) {
                val current = Position(Column.values()[colIndex], Row.values()[rowIndex])
                val figureAtCurrent = board.getFigureAt(current)
                if (figureAtCurrent == null) {
                    moves.add(current)
                } else {
                    if (figureAtCurrent.color() != color) {
                        moves.add(current)
                    }
                    break
                }
                colIndex += direction.deltaX
                rowIndex += direction.deltaY
            }
        }
        return moves
    }

    fun tryAddMoveInDirection(
        from: Position,
        direction: Direction,
        board: ChessBoard,
        color: Color
    ): Position? {
        val deltaX = direction.deltaX
        val deltaY = direction.deltaY
        val newColumnIndex = from.column.ordinal + deltaX
        val newRowIndex = from.row.ordinal + deltaY

        return if (
            newColumnIndex in Column.values().indices &&
            newRowIndex in Row.values().indices
        ) {
            val target = Position(
                Column.values()[newColumnIndex],
                Row.values()[newRowIndex]
            )
            val destination = board.getFigureAt(target)
            if (destination == null || destination.color() != color) {
                target
            } else null
        } else null
    }

}