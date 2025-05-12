package hwr.oop.CLI

import hwr.oop.ChessBoard
import hwr.oop.Move
import hwr.oop.Position

class ChessGameCLI(private val board: ChessBoard) {
    private var isWhiteTurn = true
    private var gameRunning = false
    private var gameId = ""

    fun start() {
        println("Willkommen beim Schachspiel!")
        while (true) {
            val input = readLine()?.trim()?.toLowerCase() ?: continue

            when {
                input.startsWith("chess new_game") -> handleNewGame(input)
                input == "exit" -> {
                    println("Spiel beendet.")
                    break
                }
                input == "display" -> handleDisplay()
                input.matches(Regex("move [a-h][1-8] [a-h][1-8]")) -> handleMove(input)
                input.startsWith("moves") -> handleShowMoves(input)
                input == "help" -> showHelp()
                else -> println("Ungültiger Befehl. Geben Sie 'help' für eine Liste der Befehle ein.")
            }
        }
    }

    private fun handleNewGame(input: String) {
        gameId = input.removePrefix("chess new_game").trim()
        if (gameId.isEmpty()) {
            println("Fehler: Keine ID-Nummer angegeben.")
            return
        }

        val board = ChessBoard.emptyBoard()

        gameRunning = true
        isWhiteTurn = true
        println("Neues Spiel gestartet mit ID: $gameId")
        board.displayBoard()
    }

    private fun handleMove(input: String) {
        if (!gameRunning) {
            println("Starten Sie zuerst ein neues Spiel mit 'chess new_game <id>'")
            return
        }

        val parts = input.split(" ")
        val from = Position(parts[1][0], parts[1][1].toString().toInt())
        val to = Position(parts[2][0], parts[2][1].toString().toInt())

        val figure = board.getFigureAt(from)
        if (figure == null) {
            println("Keine Figur an dieser Position.")
            return
        }

        if (figure.isWhite != isWhiteTurn) {
            println("${if (isWhiteTurn) "Weiß" else "Schwarz"} ist am Zug!")
            return
        }

        val move = Move(from, to, board)
        if (move.execute()) {
            isWhiteTurn = !isWhiteTurn
            board.displayBoard()
            println("${if (isWhiteTurn) "Weiß" else "Schwarz"} ist am Zug.")
        }
    }

    private fun handleDisplay() {
        if (!gameRunning) {
            println("Kein aktives Spiel.")
            return
        }
        board.displayBoard()
    }

    private fun handleShowMoves(input: String) {
        if (!gameRunning) {
            println("Kein aktives Spiel.")
            return
        }

        val pos = input.split(" ").getOrNull(1) ?: return
        if (!pos.matches(Regex("[a-h][1-8]"))) {
            println("Ungültige Position. Format: a1-h8")
            return
        }

        val position = Position(pos[0], pos[1].toString().toInt())
        val moves = board.getFigureAt(position)?.availableMoves(position, board)
        println("Verfügbare Züge für $pos: ${moves?.map { "${it.column}${it.row}" }?.joinToString(", ") ?: "Keine"}")
    }

    private fun showHelp() {
        println("""
            Verfügbare Befehle:
            chess new_game <id> - Startet ein neues Spiel
            move <von> <nach>  - Bewegt eine Figur (z.B. 'move a2 a4')
            moves <position>    - Zeigt mögliche Züge (z.B. 'moves a2')
            display            - Zeigt das aktuelle Brett
            help              - Zeigt diese Hilfe
            exit              - Beendet das Spiel
        """.trimIndent())
    }
}