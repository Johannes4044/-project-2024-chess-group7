package hwr.oop

data class Move(val from: Position, val to: Position, val board: ChessBoard) {
    override fun toString(): String {
        return "Zug von ${from.Column}${from.Row} nach ${to.Column}${to.Row}"
    }

    fun isValid(): Boolean {
        val figure = board.getFigureAt(from) ?: return false
        return figure.canMove(from, to, board)
    }

    fun isCapture(): Boolean {
        val targetFigure = board.getFigureAt(to)
        val movingFigure = board.getFigureAt(from)
        return targetFigure != null && movingFigure != null && targetFigure.isWhite != movingFigure.isWhite
    }

    fun execute(): Boolean {
        if (!isValid()) {
            println("Ungültiger Zug!")
            return false
        }

        if (isCapture()) {
            println("Figur wurde geschlagen!")
        }

        return board.move(from, to)
    }
}
////data class Move (val from: Position, val to: Position,val board: = mutableMapOf<Position, Figures>()) : Boolean {
//    override fun toString(): String {
//        return "Move from $from to $to"
//    }
//
//    fun getFigureAt(from: Position,to: Position): Figures? = board[from]
//    fun isValid(): Boolean {
//        //TODO: Implement logic to check if the move is valid
//    }
//
//    fun isCapture(): Boolean {
//        //TODO: Implement logic to check if the move is a capture
//    }
//
////    fun isPromotion(): Boolean {
////        //TODO: Implement logic to check if the move is a promotion
////    }
//}