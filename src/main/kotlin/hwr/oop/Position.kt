package hwr.oop

data class Position(val column: Column, val row: Row) {
    override fun toString() = "$column$row"

    companion object {
        fun from(column: Char, row: Int): Position? =
            values().find { it.column == column && it.row == row }
    }
}