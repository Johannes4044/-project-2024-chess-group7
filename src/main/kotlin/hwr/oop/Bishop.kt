import hwr.oop.ChessBoard
import hwr.oop.Figures
import hwr.oop.Position


class Bishop(isWhite: Boolean) : Figures(isWhite) {
    override fun symbol() = if (isWhite) "l" else "L"

    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean{
        return true
    }
}