package hwr.oop

interface Figure {
    val isWhite: Boolean
    fun symbol(): String
    fun availableMoves(from: Position, board: ChessBoard): List<Position>
}