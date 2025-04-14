package hwr.oop

import hwr.oop.figuren.*

class SchachBrett {
    val felder: MutableMap<Position, SchachFigur?> = mutableMapOf()

    init {
        // Initialisiere das Schachbrett mit leeren Feldern
        for (i in 0..7) {
            for (j in 'a'..'h') {
                felder[Position(j, i)] = null
            }
        }
    }

    fun initializeBoard() {
        // Bauern initialisieren
        for (i in 0..7) {
            felder[Position('g', i)] = Bauer("weiss", Position('g', i))
            felder[Position('b', i)] = Bauer("schwarz", Position('b', i))
        }

        // Türme initialisieren
        felder[Position('h', 0)] = Turm("weiss", Position('h', 0))
        felder[Position('a', 0)] = Turm("schwarz", Position('a', 0))
        felder[Position('h', 7)] = Turm("weiss", Position('h', 7))
        felder[Position('a', 7)] = Turm("schwarz", Position('a', 7))
        // Springer initialisieren
        felder[Position('h', 1)] = Springer("weiss", Position('h', 1))
        felder[Position('a', 1)] = Springer("schwarz", Position('a', 1))
        felder[Position('h', 6)] = Springer("weiss", Position('h', 6))
        felder[Position('a', 6)] = Springer("schwarz", Position('a', 6))

        // Läufer initialisieren
        felder[Position('h', 2)] = Laeufer("weiss", Position('h', 2))
        felder[Position('a', 2)] = Laeufer("schwarz", Position('a', 2))
        felder[Position('h', 5)] = Laeufer("weiss", Position('h', 5))
        felder[Position('a', 5)] = Laeufer("schwarz", Position('a', 5))
        // Dame initialisieren
        felder[Position('h', 3)] = Dame("weiss", Position('h', 3))
        felder[Position('a', 3)] = Dame("schwarz", Position('a', 3))

        // König initialisieren
        felder[Position('h', 4)] = Koenig("weiss", Position('h', 4))
        felder[Position('a', 4)] = Koenig("schwarz", Position('a', 4))
    }

    fun displayBoard() {
        for (x in 'a'..'h') {
            for (y in 0..7) {
                val figur = felder[Position(x, y)]
                val symbol = figur?.typ?.firstOrNull()
                if (figur != null) {
                    print(" ${if (figur.farbe == "weiss") symbol?.lowercaseChar() else symbol?.uppercaseChar()} ")
                } else {
                    print(" . ")
                }
            }
            println()
        }
    }
}

fun main() {
    val schachBrett = SchachBrett()
    schachBrett.initializeBoard()
    schachBrett.displayBoard()
}
//class ChessBoard {
//  private val board = Array(8) { Array(8) { " " } }
//
//  fun displayBoard() {
//    for (i in 0 until 8) {
//      println(" +---+---+---+---+---+---+---+---+")
//      for (j in 0 until 8) {
//        print(" | ${board[i][j]}")
//      }
//      println(" |")
//    }
//    println(" +---+---+---+---+---+---+---+---+")
//  }
//
//  fun initializeBoard() {
//    initializeBoard(board)
//  }
//}
//
//fun main() {
//   val chessBoard = ChessBoard()
//   chessBoard.initializeBoard()
//   chessBoard.displayBoard()
//}

