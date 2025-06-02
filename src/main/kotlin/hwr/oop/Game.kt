package hwr.oop

import hwr.oop.figures.FigureType
import hwr.oop.figures.King
import hwr.oop.figures.Pawn
import hwr.oop.Move

class Game {
    var board: ChessBoard = ChessBoard.fullBoard()
    var currentPlayerIsWhite: Boolean = true
    val moves = mutableListOf<Triple<Figure, Position, Position>>()
    var totalMoves = 0;

    fun startGame() {
    }

    fun makeMove(from: Position, to: Position, promotionFigure: FigureType? = null): Boolean {
        val figure = board.getFigureAt(from)?: return false
        val move = Move(from, to, board)


        if (move.isValid()) {
            totalMoves += 1
            move.execute()
            if (move.isCapture() || figure is Pawn) {
                totalMoves = 0 // Reset total moves if a pawn moves or a piece is captured
            }

            if (board.getFigureAt(to) is Pawn &&
                ((to.row == 8 && figure.color == Color.WHITE) || (to.row == 1 && figure.color == Color.BLACK))) {
                board.promoteFigure(to, promotionFigure)
            }
            currentPlayerIsWhite = !currentPlayerIsWhite
            return true
        }else {
            return false
        }
    }


    fun getAllMoves(board: ChessBoard): Pair<List<Position>, List<Position>> {  //gibt ein paar aus Listen wieder
        val whiteMoves = mutableListOf<Position>() //enthält alle weißen möglichen Züge
        val blackMoves = mutableListOf<Position>() //enthält alle schwarzen möglichen Züge
        val col = 'a'..'h'
        val row = 1..8

        for (column in col) {
            for (row in row) {
                val from = Position(column, row)
                val figure = board.getFigureAt(from)

                if (figure != King(Color.WHITE) && figure != null && figure.symbol()[0].isLowerCase()) {
                    val moves = figure.availableMoves(from, board)
                    whiteMoves.addAll(moves)    //fügt alle Züge einer weißen Figur zur Liste zu
                }
                if (figure != King(Color.BLACK) && figure != null && figure.symbol()[0].isUpperCase()) {
                    val moves = figure.availableMoves(from, board)
                    blackMoves.addAll(moves)    //fügt alle Züge einer schwarzen Figur zur Liste zu
                }
            }
        }
        return Pair(whiteMoves, blackMoves)
    }

    fun whiteCheck(): Boolean {
        val (_, blackMoves) = getAllMoves(board)
        val whiteKing = board.findKing(true)
        if (whiteKing == null) return false
        return blackMoves.contains(whiteKing)
    }

    fun blackCheck(): Boolean {
        val (whiteMoves, _) = getAllMoves(board)
        val blackKing = board.findKing(false)
        if (blackKing == null) return false
        return whiteMoves.contains(blackKing)
    }

    fun isGameOver(): Boolean {
        val currentIsWhite = currentPlayerIsWhite
        val (whiteMoves, blackMoves) = getAllMoves(board)


        val inCheck = if (currentIsWhite) whiteCheck() else blackCheck()
        val playerMoves = if (currentIsWhite) whiteMoves else blackMoves
        val hasMoves = playerMoves.isNotEmpty()

        if (!hasMoves) {
            return if (inCheck) {
                true
            } else {
                true
            }
        }

        if (totalMoves >= 50) {
            return true // 50-Moves rule
        }
        return false
    }

}