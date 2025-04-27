package hwr.oop

class Turm(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "t" else "T"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        return true
    }
}