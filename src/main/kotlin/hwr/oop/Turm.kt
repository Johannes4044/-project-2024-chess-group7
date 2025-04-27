package hwr.oop

class Turm(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "t" else "T"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        return true
    }
}