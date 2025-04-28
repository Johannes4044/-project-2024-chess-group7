package hwr.oop

import Laeufer


class ChessBoard {
   val pieces = mutableMapOf<Position, Figuren>()

  init {
    initializeBoard()
  }

  fun initializeBoard() {

    for (i in 'a'..'h') {
      pieces[Position(i, 2)] = Bauer(true)
    }
    for (i in 'a'..'h') {
      pieces[Position(i, 7)] = Bauer(false)
    }
    pieces[Position('a', 1)] = Turm(true)
    pieces[Position('a', 8)] = Turm(false)
    pieces[Position('h', 1)] = Turm(true)
    pieces[Position('h', 8)] = Turm(false)

    pieces[Position('b', 1)] = Springer(true)
    pieces[Position('b', 8)] = Springer(false)
    pieces[Position('g', 1)] = Springer(true)
    pieces[Position('g', 8)] = Springer(false)

    pieces[Position('c', 1)] = Laeufer(true)
    pieces[Position('c', 8)] = Laeufer(false)
    pieces[Position('f', 1)] = Laeufer(true)
    pieces[Position('f', 8)] = Laeufer(false)

    pieces[Position('d', 1)] = Dame(true)
    pieces[Position('d', 8)] = Dame(false)

    pieces[Position('e', 1)] = König(true)
    pieces[Position('e', 8)] = König(false)
  }


  fun getFigureAt(position: Position): Figuren? = pieces[position]

  fun move(from: Position, to: Position): Boolean {
    val figure = pieces[from] ?: return false
    if (figure.canMove(from, to, this)) {
      pieces.remove(from)
      pieces[to] = figure
      return true
    }
    println("Ungültiger Zug für ${figure.symbol()} von $from nach $to")
    return false
  }

  fun displayBoard() {
    for (j in 8 downTo 1) {
      for (i in 'a'..'h') {
        val pos = Position(i, j)
        val fig = pieces[pos]
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

  println("Um das Spiel zu starten, schreiben Sie: chess new_game und eine ID-Nummer:")
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


   chessBoard.displayBoard()
   chessBoard.move(Position('a', 2), Position('a', 4))
   chessBoard.displayBoard()
   chessBoard.move(Position('a', 1), Position('b', 4))
   chessBoard.displayBoard()
//   chessBoard.move(Position('e', 1), Position('e', 2))
//   chessBoard.displayBoard()
//   chessBoard.move(Position('e', 2), Position('e', 1))
//   chessBoard.displayBoard()
//   chessBoard.move(Position('d', 1), Position('h', 5))
//   chessBoard.displayBoard()
}

