package hwr.oop

data class Position(val column: Char, val row: Int) {
    override fun toString() = "$column$row"
}