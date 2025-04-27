package hwr.oop

class König(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "k" else "K"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        return true
    }
}