package hwr.oop
import hwr.oop.initializeBoard


class ChessBoard {
  private val board = Array(8) { Array(8) { " " } }

  fun displayBoard() {
    for (i in 0 until 8) {
      println(" +---+---+---+---+---+---+---+---+")
      for (j in 0 until 8) {
        print(" | ${board[i][j]}")
      }
      println(" |")
    }
    println(" +---+---+---+---+---+---+---+---+")
  }

  fun initializeBoard() {
    initializeBoard(board)
  }
}

fun main() {
   val chessBoard = ChessBoard()
   chessBoard.initializeBoard()
   chessBoard.displayBoard()
}

