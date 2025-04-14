package hwr.oop

data class Position(val Spalte: Char, val Zeile: Int) {
    override fun toString() = "$Spalte$Zeile"
}