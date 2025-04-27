import hwr.oop.ChessBoard
import hwr.oop.Figuren
import hwr.oop.Position

class Laeufer(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "l" else "L"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        return true
    }
}