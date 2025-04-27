package hwr.oop

class Springer(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "s" else "S"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        return true
    }
}