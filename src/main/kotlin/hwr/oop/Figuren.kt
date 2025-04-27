package hwr.oop

abstract class Figuren(val isWhite: Boolean) {
    abstract fun symbol(): String
    abstract fun canMove(from: Position, to: Position, board: ChessBoard): Boolean
}