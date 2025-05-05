package hwr.oop

import Bishop

class ChessBoard(val board: MutableMap<Position, Figures>) {


  companion object {
    fun emptyBoard(): ChessBoard = ChessBoard(mutableMapOf())

    fun fullBoard(): ChessBoard {
      val board = mutableMapOf<Position, Figures>()
      for (i in 'a'..'h') {
        board[Position(i, 2)] = Pawn(true)
      }
      for (i in 'a'..'h') {
        board[Position(i, 7)] = Pawn(false)
      }
      board[Position('a', 1)] = Rook(true)
      board[Position('a', 8)] = Rook(false)
      board[Position('h', 1)] = Rook(true)
      board[Position('h', 8)] = Rook(false)

      board[Position('b', 1)] = Knight(true)
      board[Position('b', 8)] = Knight(false)
      board[Position('g', 1)] = Knight(true)
      board[Position('g', 8)] = Knight(false)

      board[Position('c', 1)] = Bishop(true)
      board[Position('c', 8)] = Bishop(false)
      board[Position('f', 1)] = Bishop(true)
      board[Position('f', 8)] = Bishop(false)

      board[Position('d', 1)] = Queen(true)
      board[Position('d', 8)] = Queen(false)

      board[Position('e', 1)] = King(true)
      board[Position('e', 8)] = King(false)

      return ChessBoard(board)
    }
  }

  fun getFigureAt(position: Position): Figures? = board[position]

  fun move(from: Position, to: Position): Boolean {
    val figure = board[from] ?: return false
    if (figure.canMove(from, to, this)) {
      board.remove(from)
      board[to] = figure
      return true
    }
    println("Ungültiger Zug für ${figure.symbol()} von $from nach $to")
    return false
  }

  fun displayBoard() {
    for (j in 8 downTo 1) {
      for (i in 'a'..'h') {
        val pos = Position(i, j)
        val fig = board[pos]
        if (fig != null) {
          print(fig.symbol() + " ")
        } else {
          print(". ")
        }
      }
      println()
    }
  }

//TODO Wenn schwarz zieht, dann muss die Figur auch schwarz sein

  fun main() {
    val chessBoard = ChessBoard.fullBoard()

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
}

