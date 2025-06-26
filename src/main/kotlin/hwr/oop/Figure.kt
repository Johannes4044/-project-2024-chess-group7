package hwr.oop

interface Figure {
    var hasMoved: Boolean

    fun color(): Color
    fun symbol(): String
    fun availableTargets(from: Position, board: ChessBoard): List<Position>
}