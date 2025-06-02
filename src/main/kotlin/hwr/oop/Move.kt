package hwr.oop
import hwr.oop.Game
import hwr.oop.figures.King
import hwr.oop.figures.Pawn
import hwr.oop.figures.Rook



data class Move(val from: Position, val to: Position, val board: ChessBoard) {

    override fun toString(): String {
        return "Zug von ${from.column}${from.row} nach ${to.column}${to.row}"
    }


    fun isValid(): Boolean {
        val figure = board.getFigureAt(from) ?: return false
        return figure.availableMoves(from, board).contains(to)
    }

    fun isCapture(): Boolean {
        val targetFigure = board.getFigureAt(to)
        val movingFigure = board.getFigureAt(from)
        return targetFigure != null && movingFigure != null && targetFigure.color != movingFigure.color    }

    fun execute(): Boolean {
        if (!isValid()) {
            error("Ungültiger Zug von ${from.column}${from.row} nach ${to.column}${to.row}")
//            println("Ungültiger Zug!")
            return false

        }

        if (isCapture()) {

//            println("Figur wurde geschlagen!")
        if (isCapture() || board.getFigureAt(from) is Pawn) {

        }

        return board.move(from, to)
    }

    fun castleKingSide(Game: Game):Boolean{
        val kingFirstMove = true
        val rookFirstMove = true
        val rookW = Rook(Color.WHITE)
        val kingW = King(Color.WHITE)

        if(kingFirstMove && rookFirstMove){
            val kingPosition = Position('e', 1)
            val kingTo = Position('b', 1)
            val rookPosition = Position('a', 1)
            val rookTo = Position('c', 1)

            board.placePieces(rookTo, rookW)
            board.placePieces(kingTo, kingW)
        }
        return true
    }
        return true
    }


}

