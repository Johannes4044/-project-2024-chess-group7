package hwr.oop

data class Position(val column: Column, val row: Row) {
    override fun toString() = "$column$row"

    companion object {
        fun from(column: Char, row: Int): Position? {
            val col = Column.values().find { it.column == column }
            val r = Row.values().find { it.row == row }
            return if (col != null && r != null) Position(col, r) else null
        }
    }
}
