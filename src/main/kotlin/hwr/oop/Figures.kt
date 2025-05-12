package hwr.oop

interface Figures {
    val isWhite: Boolean
    fun symbol(): String
    fun canMove(from: Position, to: Position, board: ChessBoard): Boolean
    fun availableMoves(from: Position, board: ChessBoard): List<Position>
}