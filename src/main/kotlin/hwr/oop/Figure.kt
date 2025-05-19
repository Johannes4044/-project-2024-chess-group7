package hwr.oop

interface Figures {
    val isWhite: Boolean
    fun symbol(): String
    fun availableMoves(from: Position, board: ChessBoard): List<Position>
}