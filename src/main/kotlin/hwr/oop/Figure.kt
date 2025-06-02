package hwr.oop

interface Figure {
    val color: Color
    fun symbol(): String
    fun availableMoves(from: Position, board: ChessBoard): List<Position>
}