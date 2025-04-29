package hwr.oop

import Bishop


class ChessBoard {


  companion object{
    fun emptyBoard(): MutableMap<Position, Figures>{
      return mutableMapOf<Position, Figures>()
    }
    fun fullBoard(): MutableMap<Position, Figures> {
      val Board = ChessBoard.emptyBoard()
      for (i in 'a'..'h') {
        Board[Position(i, 2)] = Pawn(true)
      }
      for (i in 'a'..'h') {
        Board[Position(i, 7)] = Pawn(false)
      }
      Board[Position('a', 1)] = Rook(true)
      Board[Position('a', 8)] = Rook(false)
      Board[Position('h', 1)] = Rook(true)
      Board[Position('h', 8)] = Rook(false)

      Board[Position('b', 1)] = Knight(true)
      Board[Position('b', 8)] = Knight(false)
      Board[Position('g', 1)] = Knight(true)
      Board[Position('g', 8)] = Knight(false)

      Board[Position('c', 1)] = Bishop(true)
      Board[Position('c', 8)] = Bishop(false)
      Board[Position('f', 1)] = Bishop(true)
      Board[Position('f', 8)] = Bishop(false)

      Board[Position('d', 1)] = Queen(true)
      Board[Position('d', 8)] = Queen(false)

      Board[Position('e', 1)] = King(true)
      Board[Position('e', 8)] = King(false)

      return Board
    }
  }

  fun getFigureAt(position: Position): Figures? = ChessBoard.fullBoard()[position]

  fun move(from: Position, to: Position): Boolean {
    val figure = ChessBoard.fullBoard()[from] ?: return false
    if (figure.canMove(from, to, this)) {
      ChessBoard.fullBoard().remove(from)
      ChessBoard.fullBoard()[to] = figure
      return true
    }
    println("Ungültiger Zug für ${figure.symbol()} von $from nach $to")
    return false
  }

  fun displayBoard() {
    for (j in 8 downTo 1) {
      for (i in 'a'..'h') {
        val pos = Position(i, j)
        val fig = ChessBoard.fullBoard()[pos]
        if (fig != null) {
          print(fig.symbol() + " ")
        } else {
          print(". ")
        }
      }
      println()
    }
  }
}
//TODO Wenn schwarz zieht, dann muss die Figur auch schwarz sein

fun main() {
  val chessBoard = ChessBoard()

 /* println("Um das Spiel zu starten, schreiben Sie: chess new_game und eine ID-Nummer:")
  val satz = readLine()?.trim()
  var id1 = ""

  if (satz != null && satz.startsWith("chess new_game")) {
    id1 = satz.removePrefix("chess new_game").trim()
    if (id1.isNotEmpty()) {
      println("Das Spiel startet mit der ID: $id1")
      chessBoard.displayBoard()
    } else {
      println("Fehler: Keine ID-Nummer angegeben.")
    }
  } else {
    println("Fehler: Ungültige Eingabe. Bitte schreiben Sie: game start und eine ID-Nummer.")
  }

*/
   chessBoard.displayBoard()
}

