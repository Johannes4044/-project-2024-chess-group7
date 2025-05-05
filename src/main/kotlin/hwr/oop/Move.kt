package hwr.oop

data class Move (val from: Position, val to: Position,val board: = mutableMapOf<Position, Figures>()) {
    override fun toString(): String {
        return "Move from $from to $to"
    }

    fun getFigureAt(position: Position): Figures? = Board[position]
    fun isValid(): Boolean {
        //TODO: Implement logic to check if the move is valid
    }

    fun isCapture(): Boolean {
        //TODO: Implement logic to check if the move is a capture
    }

//    fun isPromotion(): Boolean {
//        //TODO: Implement logic to check if the move is a promotion
//    }
}