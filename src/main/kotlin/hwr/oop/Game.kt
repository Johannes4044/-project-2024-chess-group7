package hwr.oop

class Game(private val board: ChessBoard) {
    private var isWhiteTurn = true
    private var moveCount = 0
    private var isGameOver = false
    private var winner: String? = null

    fun draw() {
        println("\n=== Schachspiel ===")
        println("Zug ${moveCount + 1}: ${if (isWhiteTurn) "Weiß" else "Schwarz"} ist am Zug")
        println("   a b c d e f g h")
        println("  -----------------")

        for (row in 8 downTo 1) {
            print("$row |")
            for (col in 'a'..'h') {
                val figure = board.getFigureAt(Position(col, row))
                val symbol = figure?.symbol() ?: "."
                print(" $symbol")
            }
            println(" |")
        }
        println("  -----------------")
        println("   a b c d e f h")
    }

    fun surrender() {
        val currentPlayer = if (isWhiteTurn) "Weiß" else "Schwarz"
        val winner = if (isWhiteTurn) "Schwarz" else "Weiß"
        isGameOver = true
        this.winner = winner
        println("$currentPlayer gibt auf. $winner gewinnt das Spiel!")
    }

    fun isGameOver(): Boolean = isGameOver

    fun getWinner(): String? = winner
}