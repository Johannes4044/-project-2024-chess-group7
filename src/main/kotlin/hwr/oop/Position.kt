package hwr.oop

data class Position(val Column: Char, val Row: Int) {
    override fun toString() = "$Column$Row"
}