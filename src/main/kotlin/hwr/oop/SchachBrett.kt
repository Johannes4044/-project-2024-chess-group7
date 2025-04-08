package hwr.oop
import hwr.oop.Bauer
import hwr.oop.Laeufer


// Class representing the chessboard
class ChessBoard {
  private val board = Array(8) { Array(8) { " " } }  // 8x8 board initialized with empty spaces

  // Function to print the chessboard
  fun displayBoard() {
    for (i in 0 until 8) {
      for (j in 0 until 8) {
        print(" ${board[i][j]} ")
      }
      println()  // Newline after each row
    }
  }

  // Function to initialize the pieces on the board
  fun initializeBoard() {
    val bauernW = mutableListOf<Bauer>()
    val bauernB = mutableListOf<Bauer>()
    for (i in 0..7) {
      bauernW.add(Bauer(6, i, "weiss", true, "b"))
    }
    for (i in 0..7) {
      bauernB.add(Bauer(1, i, "schwarz", true, "B"))
    }

    val laeuferW = mutableListOf<Laeufer>()
    laeuferW.add(Laeufer(7, 2, "weiss", true, "l"))
    laeuferW.add(Laeufer(7, 5, "weiss", true, "l"))

    val laeuferB = mutableListOf<Laeufer>()
    laeuferB.add(Laeufer(0, 2, "schwarz", true, "L"))
    laeuferB.add(Laeufer(0, 5, "schwarz", true, "L"))

    for (i in 0..7) {
      board[bauernW[i].positionX][bauernW[i].positionY] = bauernW[i].Name
    }

    for (i in 0..7) {
      board[bauernB[i].positionX][bauernB[i].positionY] = bauernB[i].Name// Set the position of the pawn on the board
    }
    for (i in 0..1) {
      board[laeuferW[i].positionX][laeuferW[i].positionY] = laeuferW[i].Name
    }

    for (i in 0..1) {
      board[laeuferB[i].positionX][laeuferB[i].positionY] = laeuferB[i].Name// Set the position of the pawn on the board
    }


    for (i in 2 until 6) {
      for (j in 0 until 8) {
        board[i][j] = " "  // Empty spaces in the middle of the board
      }
    }
  }
}
fun main() {
  val chessBoard = ChessBoard()
  chessBoard.initializeBoard()
  chessBoard.displayBoard()
}
