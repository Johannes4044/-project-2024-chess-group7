package hwr.oop

interface Figure {
    fun color(): Color
    fun symbol(): String
    fun availableTargets(from: Position, board: ChessBoard): List<Position>
}