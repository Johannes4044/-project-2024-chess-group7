package hwr.oop

class Dame(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "d" else "D"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        return true
    }
}