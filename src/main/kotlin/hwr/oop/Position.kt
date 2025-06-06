package hwr.oop

data class Position(val column: Column, val row: Row) {
    override fun toString() = "$column$row"
}