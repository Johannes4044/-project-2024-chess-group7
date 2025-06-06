package hwr.oop
import hwr.oop.Game
import hwr.oop.figures.King
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

    fun whiteCastling(figure: Figure, game: Game): Boolean {
        if (game.currentPlayerIsWhite){
            if(to == Position('g',1)) { //white kingside
                for(col in 'e'..'g'){
                    if (!board.isSpaceFree(game, Position(col, 1),true))return false
                }
                for(col in 'f'..'g'){
                    if (board.getFigureAt(Position(col,1)) != null) return false
                }

                val rookFrom = Position('h', 1)
                val toRook = Position('f', 1)
                val rook = board.getFigureAt(rookFrom)
                if(rook != null) {
                    board.placePieces(to, figure)
                    board.placePieces(toRook, rook)
                    board.removePiece(from)
                    board.removePiece(rookFrom)
                    return true
                }
            }
            if(to == Position('c',1)) { //white queenside
                for(col in 'c'..'e'){
                    if (!board.isSpaceFree(game, Position(col, 1),true))return false
                }
                for(col in 'b'..'d'){
                    if (board.getFigureAt(Position(col,1)) != null) return false
                }
                val rookFrom = Position('a', 1)
                val toRook = Position('d', 1)
                val rook = board.getFigureAt(rookFrom)
                if(rook != null) {
                    board.placePieces(to, figure)
                    board.placePieces(toRook, rook)
                    board.removePiece(from)
                    board.removePiece(rookFrom)
                    return true
                }
            }
            return false
        }
        return false
    }

    fun blackCastling(figure: Figure, game: Game): Boolean {
        if (!game.currentPlayerIsWhite) {
            if(to == Position('g',8)) { //black kingside
                for(col in 'e'..'g'){
                    if (!board.isSpaceFree(game, Position(col, 1),false))return false
                }
                for(col in 'f'..'g'){
                    if (board.getFigureAt(Position(col,8)) != null) return false
                }
                val rookFrom = Position('h', 8)
                val toRook = Position('f', 8)
                val rook = board.getFigureAt(rookFrom)
                if(rook != null) {
                    board.placePieces(to, figure)
                    board.placePieces(toRook, rook)
                    board.removePiece(from)
                    board.removePiece(rookFrom)
                    return true
                }
            }
            if(to == Position('c',8)) {//black queenside
                for(col in 'c'..'e'){
                    if (!board.isSpaceFree(game, Position(col, 1),false)) return false
                }
                for(col in 'b'..'d'){
                    if (board.getFigureAt(Position(col,8)) != null) return false
                }
                val rookFrom = Position('a', 8)
                val toRook = Position('d', 8)
                val rook = board.getFigureAt(rookFrom)
                if(rook != null) {
                    board.placePieces(to, figure)
                    board.placePieces(toRook, rook)
                    board.removePiece(from)
                    board.removePiece(rookFrom)
                    return true
                }
            }
            return false
        }
        return false
    }
}
