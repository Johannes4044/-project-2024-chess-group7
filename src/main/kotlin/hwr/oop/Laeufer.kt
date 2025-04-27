import hwr.oop.ChessBoard
import hwr.oop.Figuren
import hwr.oop.Position

class Laeufer(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "l" else "L"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        return true
    }
}