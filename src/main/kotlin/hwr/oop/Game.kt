package hwr.oop

class Game {
    val board: ChessBoard = ChessBoard.fullBoard()
    var currentPlayerIsWhite: Boolean = true

    fun startGame() {
        board.displayBoard()
        println("Das Spiel beginnt!")
    }

    fun makeMove(from: Position, to: Position): Boolean {
        val figure = board.getFigureAt(from)
        if (figure == null || figure.isWhite != currentPlayerIsWhite) {
            println("Ungültiger Zug: Es ist der Zug des ${if (currentPlayerIsWhite) "weißen" else "schwarzen"} Spielers.")
            return false
        }

        if (figure.canMove(from, to, board)) {
            board.move(from, to)
            currentPlayerIsWhite = !currentPlayerIsWhite
            println("Zug erfolgreich!")
            return true
        } else {
            println("Ungültiger Zug!")
            return false
        }
        currentPlayerIsWhite = !currentPlayerIsWhite
    }


    fun isGameOver(): Boolean {
        fun isKingInCheck(whiteTurn: Boolean): Boolean {
            val kingPosition = board.findKing(whiteTurn) ?: return false

            for (position in board.getAllPositions() as List<Position>) {
                val figure = board.getFigureAt(position)
                if (figure != null && figure.isWhite != whiteTurn) {
                    if (figure.availableMoves(position, board).contains(kingPosition)) {
                        return true
                    }
                }
            }
            return false
        }
        return false
    }
}