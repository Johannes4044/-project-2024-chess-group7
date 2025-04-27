package hwr.oop

abstract class Figuren(val isWhite: Boolean) {
    abstract fun symbol(): String
    open fun canMove(from: Position, to: Position, board: ChessBoard): Boolean = true
}