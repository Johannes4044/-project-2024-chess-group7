package hwr.oop

import hwr.oop.CLI.ChessGameCLI

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

    fun fromFEN(fen: String): ChessBoard {
      val board = mutableMapOf<Position, Figures>()
      val rows = fen.split(" ")[0].split("/")  // nur Feldbelegung
      var rowIndex = 8

      for (row in rows) {
        var colIndex = 'a'
        for (char in row) {
          when {
            char.isDigit() -> {
              colIndex += char.digitToInt()
            }
            else -> {
              val isWhite = char.isLowerCase()
              val figure = when (char.lowercaseChar()) {
                'b' -> Pawn(isWhite)
                't' -> Rook(isWhite)
                's' -> Knight(isWhite)
                'l' -> Bishop(isWhite)
                'd' -> Queen(isWhite)
                'k' -> King(isWhite)
                else -> throw IllegalArgumentException("Ungültige Figur in FEN: $char")
              }
              board[Position(colIndex, rowIndex)] = figure
              colIndex++
            }
          }
        }
        rowIndex--
      }

      return ChessBoard(board)
    }
  }

  fun getFigureAt(position: Position): Figures? = board[position]

  fun move(from: Position, to: Position, promoteTo: ((Boolean) -> Figures)? = null): Boolean {
    val figure = board[from] ?: return false
    if (figure.canMove(from, to, this)) {
      board.remove(from)
      if (figure is Pawn && (to.row == 8 && figure.isWhite || to.row == 1 && !figure.isWhite)) {
        board[to] = promoteTo?.invoke(figure.isWhite) ?: Queen(figure.isWhite)
        println("Bauer wird zur Dame befördert!")
      }
      else {
        board[to] = figure
      }
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


  fun toFEN(): String{
    val fen = StringBuilder()
    for (j in 8 downTo 1) {
      var emptyCount = 0
      for (i in 'a'..'h') {
        val pos = Position(i, j)
        val fig = board[pos]
        if (fig != null) {
          if (emptyCount > 0) {
            fen.append(emptyCount)
            emptyCount = 0
          }
          fen.append(fig.symbol())
        } else {
          emptyCount++
        }
      }
      if (emptyCount > 0) {
        fen.append(emptyCount)
      }
      if (j > 1) {
        fen.append("/")
      }
    }
    return(fen.toString())
  }
  fun getAllFigures(whiteTurn: Boolean): Any {

    val allFigures = mutableListOf<Figures>()
    for (entry in board.entries) {
      if (entry.value.isWhite == whiteTurn) {
        allFigures.add(entry.value)
      }
    }
    return allFigures
  }

  fun findKing(whiteTurn: Boolean): Position? {
    for (entry in board.entries) {
      if (entry.value is King && entry.value.isWhite == whiteTurn) {
        return entry.key
      }
    }
    return null
  }

  fun getAllPositions(): Any {
    val allPositions = mutableListOf<Position>()
    for (entry in board.entries) {
      allPositions.add(entry.key)
    }
    return allPositions
  }
}

fun main() {
  val chessBoard = ChessBoard.fullBoard()

    val cli = ChessGameCLI(chessBoard)
    cli.start()
}