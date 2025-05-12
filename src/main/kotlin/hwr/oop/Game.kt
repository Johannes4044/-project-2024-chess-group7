package hwr.oop

class Game {
    private val board: ChessBoard = ChessBoard.fullBoard()
    private var currentPlayerIsWhite: Boolean = true

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
    }

    fun isGameOver(): Boolean {
        //TODO Logik zur Überprüfung, ob das Spiel vorbei ist (z. B. Schachmatt, Patt)
        return false
    }
}